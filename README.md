streaming-file-server [![build](https://travis-ci.org/daggerok/streaming-file-server.svg?branch=master)](https://travis-ci.org/daggerok/streaming-file-server)
=====================

full-stack java file server based on spring-boot / spring-* with no limitation for upload and download files

[**try it locally**](https://github.com/daggerok/streaming-file-server/releases)

### Installation

**with postgres in using docker**

```bash
export VERSION="3.0.0"

# database
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/docker-compose.yml
docker-compose -f docker-compose.yml up -d

# file-items data service
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/file-items-service-$VERSION.jar
bash file-items-service-$VERSION.jar

# file server
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/file-server-$VERSION.jar
bash file-server-$VERSION.jar --app.upload.path=./path/to/file-storage

# cleanup
docker-compose -f docker-compose.yml down -v
```

or simply using shell-script

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/application.bash

# start
bash application.bash start ./path/to/file-storage

# stop
bash application.bash stop

# cleanup
bash application.bash clean ./path/to/file-storage
```

*note: tested on osx with localhost docker*

installed binaries: `wget`, `docker-compose`, `bash` and of course `java` are required

for windows use https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/application.cmd

```cmd
@rem start
application.cmd start path\to\file-storage

@rem stop
application.cmd stop

@rem cleanup
application.cmd clean path\to\file-storage
```

*note: tested on windows 10 with localhost docker for postgres*

installed binaries: `which`, `wget`, `docker-compose`, `taskkill`, `mkdir` and of course java (binaries: `java` and `jps`) are required

**with h2 in-memory database**

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/file-items-service-$VERSION.jar
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/file-server-$VERSION.jar

bash file-items-service-$VERSION.jar --spring.profiles.active=db-h2
bash file-server-$VERSION.jar --app.upload.path=./path/to/file-storage
```

or simply shell script for h2

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/application-h2.bash

# start
bash application-h2.bash start ./path/to/file-storage

# stop
bash application-h2.bash stop

# cleanup
bash application-h2.bash clean ./path/to/file-storage
```

for windows use https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/application-h2.cmd

```cmd
@rem start
application-h2.cmd start path\to\file-storage

@rem stop
application-h2.cmd stop

@rem cleanup
application-h2.cmd clean path\to\file-storage
```

*note: tested on windows 10*

installed binaries: `which`, `del`, `wget`, `taskkill`, `mkdir` and of course `java`, `jps` are required

### development

```sh
bash gradlew clean build
bash gradlew :a-m:f-i-r-s:bootRun
bash gradlew :a-m:s-f-s:bootRun
open http://localhost:8002 # enjoy :)

# cleanup
bash gradlew composeDown
bash gradlew --stop
```

awesome JGiven reports!

```sh
bash gradlew clean test jgiven
open application-modules/streaming-file-server/jgiven-reports/html/index.html
```

### technology stack

- [spring](https://spring.io/)
  - spring-boot
  - spring-mvc ([mustache template engine](http://mustache.github.io/))
  - spring-data, QueryDSL, spring-data-rest, spring-data-jpa
  - spring-utils, spring-devtools, apache fileUpload, [lombok](https://projectlombok.org/), [vavr](http://www.vavr.io/)
  - common error handling (home redirect)
  - ~~spring-social (facebook login required for upload ability)~~ replaced with basic spring-security for now
  - ~~spring annotations (`@Get`, `@Post`, `@WebPage`)~~ (use `@GetMapping`, `@PostMapping`, etc...)
  - ~~spring-data REST HAL browser~~ (removed)
- code / architecture splitting into micro-services
- [bootstrap](http://getbootstrap.com/)
- [bootstrap fileinput](http://plugins.krajee.com/file-input)
- [jgiven](http://jgiven.org/)
- [powermock](https://github.com/jayway/powermock/wiki)
- [mockito](http://mockito.org/)
- [h2](http://www.h2database.com/html/cheatSheet.html)
- [postgres](https://www.postgresql.org/)
- [docker](https://www.docker.com/)
- [gradle](http://gradle.org/)
- [travis CI](https://travis-ci.org/)

**todo**

- support removing files (rly..? as minimum from db)
- improve files-db sync (replace FileSystem with GridFS or ...?)
- bi-directional files synchronization with spring scheduling or batch
- backup, restore, migration
- add more security...

_before start, install all what you needed_

- java [from here](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or [from here](https://java.com/ru/download/)
- if you on windows: [powershell](https://www.microsoft.com/en-us/download/details.aspx?id=34595) and [scoop](https://github.com/lukesampson/scoop)
