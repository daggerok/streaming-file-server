package daggerok.fileitems.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class FileItemsRoutes {

  @Bean
  public RouterFunction<ServerResponse> routes(final FileItemsHandler handler) {
    return
        route(GET("/api/v1/file-items"), handler::getAll)
            .andRoute(GET("/api/v1/file-items/{id}"), handler::getById)
            .andRoute(GET("/api/v1/file-items/like/{filename}"), handler::searchAny)
            .andRoute(POST("/api/v1/file-items"), handler::save)
            .andRoute(POST("/api/v1/file-items/all"), handler::saveAll)
        ;
  }
}
