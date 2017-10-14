package daggerok.client;

import daggerok.config.props.FileItemsRestServiceProps;
import daggerok.client.model.FileItem;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

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

    val item = Try.of(() -> restTemplate.getForEntity(url, FileItem.class))
                  .map(HttpEntity::getBody)
                  .getOrElseGet(throwable -> null);

    return ofNullable(item);
  }

  public List<FileItem> findByFilenameContainingIgnoreCase(final String filename) {

    if (StringUtils.isEmpty(filename)) {
      return findAll();
    }

    val url = UriComponentsBuilder.fromHttpUrl(config.getUrl())
                                  .path("/api/v1/file-items/like")
                                  .pathSegment(filename)
                                  .build()
                                  .toString();

    val list = Try.of(() -> restTemplate.getForEntity(url, ArrayList.class))
                  .map(HttpEntity::getBody)
                  .getOrElseGet(throwable -> new ArrayList<FileItem>());

    return Try.of(() -> (List<FileItem>) list)
              .getOrElseGet(throwable -> new ArrayList<>());
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

    val request = new HttpEntity<>(fileItems);
    val url = UriComponentsBuilder.fromHttpUrl(config.getUrl())
                                  .path("/api/v1/file-items/all")
                                  .build()
                                  .toString();

    val list = Try.of(() -> restTemplate.postForEntity(url, request, ArrayList.class))
                  .map(HttpEntity::getBody)
                  .getOrElseGet(throwable -> new ArrayList<FileItem>());

    return Try.of(() -> (List<FileItem>) list)
              .getOrElseGet(throwable -> new ArrayList<>())
              .stream();
  }
}
