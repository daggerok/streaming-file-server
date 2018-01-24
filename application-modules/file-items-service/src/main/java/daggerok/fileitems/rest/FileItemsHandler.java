package daggerok.fileitems.rest;

import daggerok.fileitems.domain.FileItem;
import daggerok.fileitems.domain.FileItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileItemsHandler {

  final FileItemRepository repository;

  public Mono<ServerResponse> getAll(final ServerRequest request) {

    return json().body(Flux.fromIterable(repository.findAll())
                           .subscribeOn(Schedulers.elastic()), FileItem.class);
  }

  public Mono<ServerResponse> searchAny(final ServerRequest request) {

    final String filename = request.pathVariable("filename");

    return json().body(repository.findAny(filename)
                                 .subscribeOn(Schedulers.elastic()), FileItem.class);
  }

  public Mono<ServerResponse> getById(final ServerRequest request) {

    final String id = request.pathVariable("id");

    return json().body(Mono.just(repository.findById(Long.valueOf(id))
                                           .orElseThrow(() -> new RuntimeException(
                                               format("file item with id %s wasn't found.", id))))
                           .subscribeOn(Schedulers.elastic()), FileItem.class);
  }

  @Transactional
  public Mono<ServerResponse> save(final ServerRequest request) {

    return created().body(request.bodyToMono(FileItem.class)
                                 .flatMap(repository::saveOrUpdate)
                                 .subscribeOn(Schedulers.elastic()), FileItem.class);
  }

  @Transactional
  public Mono<ServerResponse> saveAll(final ServerRequest request) {

    return created().body(request.bodyToFlux(FileItem.class)
                                 .collectList()
                                 .map(repository::saveOrUpdateAll)
                                 .subscribeOn(Schedulers.elastic())
                                 .flatMap(Flux::collectList), List.class);
  }

  private static ServerResponse.BodyBuilder json() {
    return ok().contentType(APPLICATION_JSON_UTF8);
  }

  private static ServerResponse.BodyBuilder created() {
    return status(CREATED).contentType(APPLICATION_JSON_UTF8);
  }
}
