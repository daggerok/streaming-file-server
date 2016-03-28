package com.daggerok.spring.streaming.fileserver.service;

import com.daggerok.spring.streaming.fileserver.domain.FileItem;
import com.daggerok.spring.streaming.fileserver.service.api.FileService;
import com.daggerok.spring.streaming.fileserver.service.util.FileItemUtil;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static com.daggerok.spring.streaming.fileserver.domain.FileType.FILE;
import static java.lang.String.format;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.nio.file.Files.walk;
import static org.apache.tomcat.util.http.fileupload.IOUtils.copyLarge;
import static org.springframework.util.FileCopyUtils.copy;

@Service
public class FileServiceBean implements FileService {
    @Value("${app.download.path}")
    String downloadPath;

    @Value("${app.upload.path}")
    String uploadPath;

    @Override
    @SneakyThrows
    public Stream<FileItem> getDownloads() {
        Objects.requireNonNull(downloadPath, "downloadPath is null, please, provide app.send.path variable");

        Path base = Paths.get(downloadPath);

        if (Files.notExists(base)) {
            Files.createDirectory(base);
        }

        return walk(Paths.get(downloadPath), FOLLOW_LINKS)
                .filter(Files::isReadable)
                .filter(Files::isWritable)
                .filter(Files::isRegularFile)
                .map(FileServiceBean::normalizeAbsolute)
                .map(FileServiceBean::mapToFileItem);
    }

    private static Path normalizeAbsolute(Path path) {
        return path.toAbsolutePath().normalize();
    }

    private static String filename(Path path) {
        return FilenameUtils.getName(string(path));
    }

    private static String extension(Path path) {
        return FilenameUtils.getExtension(string(path));
    }

    private static String string(Path path) {
        return path.toString();
    }

    @SneakyThrows
    private static FileItem mapToFileItem(Path path) {
        return new FileItem()
                .setFileType(FILE)
                .setPath(string(path))
                .setSize(Files.size(path))
                .setFilename(filename(path))
                .setExtension(extension(path))
                .setPrettySize(FileItemUtil.toString(Files.size(path)));
    }

    @Override
    @SneakyThrows
    public void send(FileItem fileItem, HttpServletResponse response) {
        if (fileItem.isFile()) {
            Path file = Paths.get(fileItem.getPath());
            @Cleanup InputStream from = Files.newInputStream(file);
            @Cleanup OutputStream to = response.getOutputStream();

            response.setHeader("Content-Disposition", format("attachment; filename=%s", file.getFileName()));
            pipe(fileItem.isLarge(), from, to);
        }
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
    public void setupUploads() {
        Path base = Paths.get(uploadPath);

        if (Files.notExists(base)) {
            Files.createDirectory(base);
        }
    }

    @Override
    @SneakyThrows
    public FileItem receive(MultipartFile file) {
        Path path = normalizeAbsolute(resolve(file.getOriginalFilename()));
        @Cleanup InputStream from = file.getInputStream();
        @Cleanup OutputStream to = Files.newOutputStream(path);

        pipe(FileItemUtil.isLarge(file.getSize()), from, to);

        return mapToFileItem(path);
    }

    /**
     * resolve path using given filename and upload base path from configuration
     */
    private Path resolve(final String filename) {
        return Paths.get(uploadPath, filename);
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
