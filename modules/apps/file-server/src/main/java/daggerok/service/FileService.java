package daggerok.service;

import daggerok.config.props.AppProps;
import daggerok.client.model.FileItem;
import daggerok.service.util.FileItemUtil;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static daggerok.client.model.FileType.FILE;
import static java.lang.String.format;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;
import static org.apache.tomcat.util.http.fileupload.IOUtils.copyLarge;
import static org.springframework.util.FileCopyUtils.copy;

/**
 * old manual style:
 * <p>
 * int EOF = -1;
 * byte[] bytes = new byte[org.springframework.util.StreamUtils.BUFFER_SIZE];
 * for (int read = from.read(bytes); read != EOF; read = from.read(bytes)) {
 * to.write(bytes, 0, read);
 * }
 * to.flush();
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

  final AppProps app;

  private static Path normalizeAbsolute(final Path path) {
    return path.toAbsolutePath().normalize();
  }

  @SneakyThrows
  private static FileItem pathToFileItem(final Path path) {

    return new FileItem().setFileType(FILE)
                         .setPath(string(path))
                         .setSize(Files.size(path))
                         .setFilename(filename(path))
                         .setExtension(extension(path))
                         .setPrettySize(FileItemUtil.toString(Files.size(path)));
  }

  private static String string(final Path path) {
    return path.toString();
  }

  private static String filename(final Path path) {
    return FilenameUtils.getName(string(path));
  }

  private static String extension(final Path path) {
    return FilenameUtils.getExtension(string(path));
  }

  /**
   * pipe from source stream to destination.
   * <p>
   * support large streams, ie > 2Gib, in case: using copyLarge
   *
   * @param isLarge large file or not
   * @param from    source stream
   * @param to      destination stream
   */
  @SneakyThrows
  private static void cp(final boolean isLarge, final InputStream from, final OutputStream to) {

    if (isLarge) {
      copyLarge(from, to);
    } else {
      copy(from, to);
    }
  }

  @SneakyThrows
  public Stream<FileItem> getDownloads() {

    requireNonNull(app.download.path, "downloadPath is null, please, provide app.send.path variable");

    val base = get(app.download.path);

    if (exists(base) && !base.toFile().isDirectory())
      deleteIfExists(base);

    if (notExists(base))
      if (base.toFile().mkdirs())
        log.error("directory {} cannot be created", base);

    val fileStream = walk(get(app.download.path), FOLLOW_LINKS);

    return fileStream.filter(Files::isReadable)
                     .filter(Files::isWritable)
                     .filter(Files::isRegularFile)
                     .map(FileService::normalizeAbsolute)
                     .map(FileService::pathToFileItem);
  }

  @SneakyThrows
  public void send(final FileItem fileItem, final HttpServletResponse response) {

    if (!fileItem.isFile()) return;

    val file = get(fileItem.getPath());
    @Cleanup val from = newInputStream(file);
    @Cleanup val to = response.getOutputStream();

    response.setHeader("Content-Disposition", format("attachment; filename=%s", file.getFileName()));
    cp(fileItem.isLarge(), from, to);
  }

  @SneakyThrows
  public void setupUploads() {

    val base = get(app.upload.path);

    if (notExists(base)) {
      createDirectory(base);
    }
  }

  @SneakyThrows
  public FileItem receive(final MultipartFile file) {

    val path = normalizeAbsolute(resolve(file.getOriginalFilename()));
    @Cleanup val from = file.getInputStream();
    @Cleanup val to = newOutputStream(path);

    cp(FileItemUtil.isLarge(file.getSize()), from, to);
    return pathToFileItem(path);
  }

  /**
   * resolve path using given filename and upload base path from configuration
   */
  private Path resolve(final String filename) {
    return get(app.upload.path, filename);
  }
}
