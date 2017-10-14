package daggerok.domain;

import daggerok.config.props.FileItemsRestServiceProps;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileItemRestClient {

  final RestTemplate restTemplate;
  final FileItemsRestServiceProps config;

  public List<FileItem> findAll() {

    val url = UriComponentsBuilder.fromHttpUrl(config.getUrl())
                                  .path("/api/v1/file-items")
                                  .build()
                                  .toString();

    val list = Try.of(() -> restTemplate.getForEntity(url, ArrayList.class))
                  .map(HttpEntity::getBody)
                  .getOrElseGet(throwable -> new ArrayList<FileItem>());

    return Try.of(() -> (List<FileItem>) list)
              .getOrElseGet(throwable -> new ArrayList<>());
  }

  public Optional<FileItem> findById(final long id) {

    val url = UriComponentsBuilder.fromHttpUrl(config.getUrl())
                                  .path("/api/v1/file-items")
                                  .pathSegment("" + id)
                                  .build()
                                  .toString();

    final FileItem item = Try.of(() -> restTemplate.getForEntity(url, FileItem.class))
                             .map(HttpEntity::getBody)
                             .getOrElseGet(throwable -> {
                               log.error(throwable.getLocalizedMessage(), throwable);
                               return null;
                             });
    return ofNullable(item);
  }

  public Stream<FileItem> findByFilenameContainingIgnoreCase(final String filename) {
    return Stream.empty();
  }

  public FileItem save(final FileItem fileItem) {

    val request = new HttpEntity<>(fileItem);
    val url = UriComponentsBuilder.fromHttpUrl(config.getUrl())
                                  .path("/api/v1/file-items")
                                  .build()
                                  .toString();

    return Try.of(() -> restTemplate.postForEntity(url, request, FileItem.class))
              .map(HttpEntity::getBody)
              .getOrElseGet(throwable -> new FileItem());
  }

  public Stream<FileItem> save(final List<FileItem> fileItems) {
    return Stream.empty();
  }
}
