# Features

- migrate to latest spring-boot `2.3.0.RELEASE`
- reactive cors filter: see `modules/apps/file-items-service/src/main/java/daggerok/config/CorsWebFluxConfigurer.java`
- global 404 fallback: see `modules/apps/file-server/src/main/java/daggerok/web/config/FallbackConfig.java`
- split architecture into micro-services
- migrate together with JPA (postgres) to nonblocking `Schedules.elastic()`
- migrate REST API from spring-mvc to `spring-webflux`
- ~~QueryDSL~~, ~~spring-data-rest,~~ spring-data-jpa
- ~~spring-social (facebook login required for upload ability)~~ replaced with basic spring-security for now
- ~~spring annotations (`@Get`, `@Post`, `@WebPage`)~~ (remove in order of embedded annotations: `@GetMapping`, `@PostMapping`, etc...)
- ~~spring-data REST HAL browser~~ (removed)
