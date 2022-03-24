# TODO

- fix Transactional after 2.2.0.M4 migration (now spring expects reactive transactional manager instead of JPA)
- migrate from postgres to reactive postgres or [R2DBC](https://r2dbc.io/), or to some reactive NoSQL (mongodb, etc...)
- migrate from Mustache template engine to [Thymeleaf](https://www.thymeleaf.org/)
- migrate from Bootstrap to [Materialize.css](https://materializecss.com/)
- migrate ~~file-items-service and~~ file-server from mvc to [reactive webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)
- add more advanced security, secure by MVC method...
- improve files-db sync (replace FileSystem with GridFS or ...?)
- backup, restore, migration...
- support removing files (rly..? as minimum from db)
- p2p, bi-directional files synchronization with spring scheduling or batch
- gradle github release plugin (at the moment, for publishing releases to github is using maven. see `pom.xml`)
- ~~migrate to Gradle 6.0+ version~~
- migrate to `org.asciidoctor.jvm.convert` Gradle plugin
- migrate [JGiven Gradle plugin](http://jgiven.org/userguide/) to 1.x version
- migrate to java 17
