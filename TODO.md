# TODO

- migrate from Mustache template engine to [Thymeleaf](https://www.thymeleaf.org/)
- migrate from Bootstrap to [Materialize.css](https://materializecss.com/)
- migrate ~~file-items-service and~~ file-server from mvc to [reactive webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)
- migrate from postgres to reactive postgres or [R2DBC](https://r2dbc.io/), or to some reactive NoSQL (mongodb, etc...)
- add more advanced security, secure by MVC method...
- improve files-db sync (replace FileSystem with GridFS or ...?)
- backup, restore, migration...
- support removing files (rly..? as minimum from db)
- p2p, bi-directional files synchronization with spring scheduling or batch
- gradle github release plugin (at the moment, for publishing releases to github is using maven. see `pom.xml`)
