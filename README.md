streaming-file-server [![build](https://travis-ci.org/daggerok/streaming-file-server.svg?branch=master)](https://travis-ci.org/daggerok/streaming-file-server)
=====================

fullstack java file server based on spring-boot / spring-* with no limitation for upload and download files

[**try it locally**](https://github.com/daggerok/streaming-file-server/releases)

### Installation:

**with postgres in using docker**

```bash
export VERSION="2.1.0"

# database
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/docker-compose-$VERSION.yml
docker-compose -f docker-compose-$VERSION.yml up -d

# application
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/streaming-file-server-$VERSION.jar
mkdir -p ./path/to/file-storage
bash streaming-file-server-$VERSION.jar --app.upload.path=./path/to/file-storage

# cleanup
docker-compose -f docker-compose-$VERSION.yml down -v
```

or simply:

```bash
# start
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/streaming-file-server-$VERSION.bash
bash streaming-file-server-$VERSION.bash start ./path/to/file-storage

# stop
bash streaming-file-server-$VERSION.bash stop

# cleanup
bash streaming-file-server-$VERSION.bash clean ./path/to/file-storage
```

*note: tested on osx, installed binaries: `which`, `rm`, `wget`, `docker-compose`, `kill`, `grep`, `awk`, `mkdir`, `bash` and of caurse `java` are required*

**with h2 in-memory database**

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/streaming-file-server-$VERSION.jar
bash streaming-file-server-$VERSION.jar --spring.profiles.active=db-h2
```

### development

```sh
gradle clean build bootRun
open http://localhost:8080 # enjoy :)

# cleanup
gradle composeDown
$ gradle --stop
```

awesome JGiven reports!

```sh
gradle clean test jgiven
open jgiven-reports/html/index.html
```

### technology stack

- [spring](https://spring.io/)
  - spring-boot
  - spring-mvc ([mustache template engine](http://mustache.github.io/))
  - spring-data, QueryDSL, spring-data-rest, spring-data-jpa
  - spring-utils, spring-devtools, apache fileUpload, [lombok](https://projectlombok.org/), [vavr](http://www.vavr.io/)
  - common error handling (home redirect)
  - spring-security / spring-social (facebook login required for upload ability)
  - ~~spring annotations (`@Get`, `@Post`, `@WebPage`)~~ (use `@GetMapping`, `@PostMapping`, etc...)
  - ~~spring-data REST HAL browser~~ (removed)
- [bootstrap](http://getbootstrap.com/)
- [bootstrap fileinput](http://plugins.krajee.com/file-input)
- [docker](https://www.docker.com/)
- [postgres](https://www.postgresql.org/)
- [h2](http://www.h2database.com/html/cheatSheet.html)
- [jgiven](http://jgiven.org/)
- [powermock](https://github.com/jayway/powermock/wiki)
- [mockito](http://mockito.org/)
- [gradle](http://gradle.org/)
- [travis CI](https://travis-ci.org/)

**todo**

- support removing files (rly..? as minimum from db)
- improve files-db sync (replace FileSystem with GridFS or ...?)
- bi-directional files synchronization with spring scheduling or batch
- backup, restore, migration
