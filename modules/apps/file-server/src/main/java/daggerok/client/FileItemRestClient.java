package daggerok.client;

import daggerok.client.model.FileItem;
import daggerok.config.props.FileItemsRestServiceProps;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileItemRestClient {

  private final RestTemplate restTemplate;
  private final FileItemsRestServiceProps config;

  @SuppressWarnings("unchecked")
  private final static Function<ArrayList, List<FileItem>> toFileItems =
      arrayList -> (List<FileItem>) arrayList;
  private final static Function<FileItemsRestServiceProps, UriComponentsBuilder> baseUri =
      config -> UriComponentsBuilder.fromHttpUrl(config.getUrl());
  private final static BiFunction<FileItemsRestServiceProps, String, UriComponentsBuilder> uriBuilder =
      (config, path) -> baseUri.apply(config).path(path);

  public List<FileItem> findAll() {
    val url = uriBuilder.apply(config, "/api/v1/file-items")
                        .build()
                        .toString();

    log.debug("findAll url: {}", url);
    return Try.of(() -> restTemplate.getForEntity(url, ArrayList.class))
              .map(HttpEntity::getBody)
              .map(toFileItems)
              .getOrElseGet(e -> {
                log.error("cannot find all items, error: {}", e.getLocalizedMessage(), e);
                return new ArrayList<>();
              });
  }

  public Optional<FileItem> findById(final long id) {
    val url = uriBuilder.apply(config, "/api/v1/file-items")
                        .pathSegment(String.valueOf(id))
                        .build()
                        .toString();

    log.debug("findById url: {}", url);
    return Try.of(() -> restTemplate.getForEntity(url, FileItem.class))
              .map(HttpEntity::getBody)
              .map(Optional::ofNullable)
              .getOrElseGet(e -> {
                log.error("cannot find item with id: {}, error: {}", id, e.getLocalizedMessage(), e);
                return Optional.empty();
              });
  }

  public List<FileItem> findByFilenameContainingIgnoreCase(final String filename) {
    if (StringUtils.isEmpty(filename)) return findAll();

    val url = uriBuilder.apply(config, "/api/v1/file-items/like")
                        .pathSegment(filename)
                        .build()
                        .toString();

    log.debug("findByFilenameContainingIgnoreCase url: {}", url);
    return Try.of(() -> restTemplate.getForEntity(url, ArrayList.class))
              .map(HttpEntity::getBody)
              .map(toFileItems)
              .getOrElseGet(e -> {
                log.error("cannot find items with filename: {}, error: {}", filename, e.getLocalizedMessage(), e);
                return new ArrayList<>();
              });
  }

  public void save(final List<FileItem> fileItems) {
    val request = new HttpEntity<>(fileItems);
    val url = uriBuilder.apply(config, "/api/v1/file-items/all")
                        .build()
                        .toString();

    log.debug("save all url: {}", url);
    Try.of(() -> restTemplate.postForEntity(url, request, ArrayList.class))
       .map(HttpEntity::getBody)
       .map(toFileItems)
       .map(Collection::stream)
       .getOrElseGet(e -> {
         log.error("cannot save file items: {}, error: {}", fileItems, e.getLocalizedMessage(), e);
         return Stream.empty();
       });
  }

  public FileItem save(final FileItem fileItem) {
    val request = new HttpEntity<>(fileItem);
    val url = uriBuilder.apply(config, "/api/v1/file-items")
                        .build()
                        .toString();

    log.debug("save url: {}", url);
    return Try.of(() -> restTemplate.postForEntity(url, request, FileItem.class))
              .map(HttpEntity::getBody)
              .getOrElseGet(e -> {
                log.error("cannot save file item: {}, error: {}", fileItem, e.getLocalizedMessage(), e);
                return new FileItem();
              });
  }
}
