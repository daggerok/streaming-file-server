package daggerok.fileitems.rest;

import daggerok.fileitems.domain.FileItem;
import daggerok.fileitems.domain.FileItemReactiveRepository;
import daggerok.fileitems.domain.FileItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Nonnull;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class FileItemsHandler {

  private final FileItemRepository repository;
  private final FileItemReactiveRepository reactiveRepository;

  public Mono<ServerResponse> getAll(@Nonnull final ServerRequest request) {
    return jsonOk().body(Flux.fromIterable(repository.findAll())
                             .subscribeOn(Schedulers.elastic()), FileItem.class);
  }

  public Mono<ServerResponse> searchAny(final ServerRequest request) {
    final String filename = request.pathVariable("filename");
    return jsonOk().body(reactiveRepository.findAny(filename)
                                           .subscribeOn(Schedulers.elastic()), FileItem.class);
  }

  public Mono<ServerResponse> getById(final ServerRequest request) {
    final String id = request.pathVariable("id");
    return jsonOk().body(Mono.just(repository.findById(Long.valueOf(id))
                                             .orElseThrow(() -> new RuntimeException(
                                                 format("file item with id %s wasn't found.", id))))
                             .subscribeOn(Schedulers.elastic()), FileItem.class);
  }

  public Mono<ServerResponse> save(final ServerRequest request) {
    return jsonCreated().body(request.bodyToMono(FileItem.class)
                                     .flatMap(reactiveRepository::saveOrUpdate)
                                     .subscribeOn(Schedulers.elastic()), FileItem.class);
  }

  public Mono<ServerResponse> saveAll(final ServerRequest request) {
    return jsonCreated().body(request.bodyToFlux(FileItem.class)
                                     .collectList()
                                     .map(reactiveRepository::saveOrUpdateAll)
                                     .subscribeOn(Schedulers.elastic())
                                     .flatMap(Flux::collectList), List.class);
  }

  private static ServerResponse.BodyBuilder jsonOk() {
    return ok().contentType(APPLICATION_JSON);
  }

  private static ServerResponse.BodyBuilder jsonCreated() {
    return status(CREATED).contentType(APPLICATION_JSON);
  }
}
