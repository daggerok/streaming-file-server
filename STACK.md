# Stack

- spring: migrate to latest spring-boot (2.1.3.RELEASE)
- cors: see `modules/apps/file-items-service/src/main/java/daggerok/config/CorsWebFluxConfigurer.java`
- 404 fallback: see `modules/apps/file-server/src/main/java/daggerok/web/config/FallbackConfig.java`
- code / architecture splitting into micro-services
- migrate postgres from blocking to nonblocking (wrapped in Schedules.elastic())
- migrate REST API from spring mvn to webflux
- ~~QueryDSL~~, ~~spring-data-rest,~~ spring-data-jpa
- ~~spring-social (facebook login required for upload ability)~~ replaced with basic spring-security for now
- ~~spring annotations (`@Get`, `@Post`, `@WebPage`)~~ (remove in order of embedded annotations: `@GetMapping`, `@PostMapping`, etc...)
- ~~spring-data REST HAL browser~~ (removed)
