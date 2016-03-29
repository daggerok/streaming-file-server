streaming-file-server [![build](https://travis-ci.org/daggerok/streaming-file-server.svg?branch=master)](https://travis-ci.org/daggerok/streaming-file-server)
=====================

java file server based on spring mvc and spring-boot with no limitation for upload and download files ..almost :)

```sh
$ git clone ... cd ...
$ gradle clean build bootRun
$ open http://localhost:8080 # enjoy :)
```

awesome JGiven reports!

```sh
gradle clean test jgiven
open jgiven-reports/html/index.html
```

h2 console:

```sh
open http://localhost:8080/h2
jdbc url: jdbc:h2:mem:fs
username: fs
password: fs
```
spring data HAL browser:

```sh
open http://localhost:8080/api/browser
```

technology stack:

- [spring](https://spring.io/)
  - spring-mvc (mustache template engine)
  - spring-boot
  - spring-data rest and jpa
  - spring-utils, apache fileUpload
  - spring annotations (@Get, @Post, @WebPage)
  - common error handling (home redirect)
  - spring-data REST HAL browser
  - spring-devtools
- [mustache](http://mustache.github.io/)
- [bootstrap](http://getbootstrap.com/)
- [bootstrap fileinput](http://plugins.krajee.com/file-input)
- [h2](http://www.h2database.com/html/cheatSheet.html)
- [lombok](https://projectlombok.org/)
- [jgiven](http://jgiven.org/)
- [powermock](https://github.com/jayway/powermock/wiki)
- [mockito](http://mockito.org/)
- [gradle](http://gradle.org/)
- [travis CI](https://travis-ci.org/)

todo:

- support removing files (rly..? as mininum from db)
- improve files-db sync (replace FileSystem with GridFS or ...?)
- bi-directional files synchronization with spring scheduling or batch
- backup, restore, migration
- spring-security, spring-social

