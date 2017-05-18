package com.daggerok.spring.streaming.fileserver.service;

import com.daggerok.spring.streaming.fileserver.config.props.AppProps;
import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.service.api.FileService;
import com.daggerok.spring.streaming.fileserver.service.util.FileItemUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.daggerok.spring.streaming.fileserver.domain.FileType.FILE;
import static java.lang.String.format;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;
import static org.apache.tomcat.util.http.fileupload.IOUtils.copyLarge;
import static org.springframework.util.FileCopyUtils.copy;

@Service
@RequiredArgsConstructor
public class FileServiceBean implements FileService {

    final AppProps app;

    private static Path normalizeAbsolute(Path path) {
        return path.toAbsolutePath().normalize();
    }

    @SneakyThrows
    private static FileItem pathToFileItem(Path path) {

        return new FileItem()
                .setFileType(FILE)
                .setPath(string(path))
                .setSize(Files.size(path))
                .setFilename(filename(path))
                .setExtension(extension(path))
                .setPrettySize(FileItemUtil.toString(Files.size(path)));
    }

    private static String string(Path path) {
        return path.toString();
    }

    private static String filename(Path path) {
        return FilenameUtils.getName(string(path));
    }

    private static String extension(Path path) {
        return FilenameUtils.getExtension(string(path));
    }

    private static FileItem mapToNewFileItem(Path path) {
        return FileItem.class.cast(pathToFileItem(path).setCreatedAt(LocalDateTime.now()));
    }

    @Override
    @SneakyThrows
    public Stream<FileItem> getDownloads() {

        requireNonNull(app.download.path, "downloadPath is null, please, provide app.send.path variable");

        val base = get(app.download.path);

        if (notExists(base)) {
            createDirectory(base);
        }

        return walk(get(app.download.path), FOLLOW_LINKS)
                .filter(Files::isReadable)
                .filter(Files::isWritable)
                .filter(Files::isRegularFile)
                .map(FileServiceBean::normalizeAbsolute)
                .map(FileServiceBean::pathToFileItem);
    }

    /* // old manual style:
    static final int EOF = -1;
    byte[] bytes = new byte[org.springframework.util.StreamUtils.BUFFER_SIZE];
    for (int read = from.read(bytes); read != EOF; read = from.read(bytes))
        to.write(bytes, 0, read);
    to.flush();
    */

    @Override
    @SneakyThrows
    public void send(FileItem fileItem, HttpServletResponse response) {

        if (!fileItem.isFile()) return;

        val file = get(fileItem.getPath());
        @Cleanup val from = newInputStream(file);
        @Cleanup val to = response.getOutputStream();

        response.setHeader("Content-Disposition", format("attachment; filename=%s", file.getFileName()));
        pipe(fileItem.isLarge(), from, to);
    }

    @Override
    @SneakyThrows
    public void setupUploads() {

        val base = get(app.upload.path);

        if (notExists(base)) {
            createDirectory(base);
        }
    }

    @Override
    @SneakyThrows
    public FileItem receive(MultipartFile file) {

        val path = normalizeAbsolute(resolve(file.getOriginalFilename()));
        @Cleanup val from = file.getInputStream();
        @Cleanup val to = newOutputStream(path);

        pipe(FileItemUtil.isLarge(file.getSize()), from, to);
        return mapToNewFileItem(path);
    }

    /**
     * resolve path using given filename and upload base path from configuration
     */
    private Path resolve(final String filename) {
        return get(app.upload.path, filename);
    }

    /**
     * pipe from source stream to destination.
     * <p>
     * support large streams, ie > 2Gib, in case: using copyLarge
     *
     * @param expression large file or not
     * @param from       source stream
     * @param to         destination stream
     */
    @SneakyThrows
    private void pipe(boolean expression, InputStream from, OutputStream to) {

        if (expression) {
            copyLarge(from, to);
        } else {
            copy(from, to);
        }
    }
}
