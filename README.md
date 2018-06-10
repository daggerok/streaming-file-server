streaming-file-server [![build](https://travis-ci.org/daggerok/streaming-file-server.svg?branch=master)](https://travis-ci.org/daggerok/streaming-file-server)
=====================

_latests VERSION: 4.3.2_

full-stack java file server based on spring-boot / spring-* with no limitation for upload and download files

- minimum java 8 is required
- with postgres: docker-ce on windows 10 is required
- if you on windows, use scoop to install java and required command line tools.

[**try it locally**](https://github.com/daggerok/streaming-file-server/releases)

### Installation

**with postgres in docker**

```bash
# database
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/docker-compose.yml
docker-compose -f docker-compose.yml up -d

# file-items data service
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/file-items-service-4.3.2.jar
bash file-items-service-4.3.2.jar --spring.profiles.active=db-pg

# file server
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/file-server-4.3.2.jar
bash file-server-4.3.2.jar --app.upload.path=./path/to/file-storage

# cleanup
docker-compose -f docker-compose.yml down -v
```

**or simply using shell-script**

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/application.bash

# start
bash application.bash start ./path/to/file-storage

# stop
bash application.bash stop

# cleanup
bash application.bash clean ./path/to/file-storage
```

*note: tested on osx with localhost docker*

installed binaries: `wget`, `docker-compose`, `bash` and of course `java` are required

**for windows use https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/application.cmd**

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
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/file-items-service-4.3.2.jar
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/file-server-4.3.2.jar

# bash file-items-service-4.3.2.jar --spring.profiles.active=db-h2 # or just:
bash file-items-service-4.3.2.jar
bash file-server-4.3.2.jar --app.upload.path=./path/to/file-storage
```

**or simply shell script for h2**

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/application-h2.bash

# start
bash application-h2.bash start ./path/to/file-storage

# stop
bash application-h2.bash stop

# cleanup
bash application-h2.bash clean ./path/to/file-storage
```

**for windows use https://github.com/daggerok/streaming-file-server/releases/download/4.3.2/application-h2.cmd**

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

**gradle**

```sh
bash gradlew clean assemble postgresUp
bash gradlew :m:a:f-i-s:bootRun
bash gradlew :m:a:f-s:bootRun

# cleanup
bash gradlew composeDown
bash gradlew --stop
```

**testing**

awesome JGiven reports!

```sh
bash gradlew clean test jgiven
open modules/apps/streaming-file-server/jgiven-reports/html/index.html
```

**quick boot all with docker**

```sh
bash gradlew clean assemble allUp -Pdebug
http -a user:password :8002
```

**run all in docker manually**

```bash
./gradelw clean assemble
docker-compose -f ./modules/docker/all/docker-compose.yml up --build --force-recreate
```

**cleanup**

NOTE: if you feel that changes take no effect, clean docker

```bash
# remove containers
docker rm -v -f $(docker ps -a|grep -v CONTAINER|awk '{print $1}')

# remove volumes
docker volume rm -f $(docker volume ls|grep -v DRIVER|awk '{print $2}')

# remove images
docker rmi -f $(docker images -a | grep -v 'IMAGE ID'| awk '{print $3}')

# remove everything
docker system prune -af --volumes
```

### known issues (deprecations)

- ~~SQLFeatureNotSupportedException: Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.~~ [fixed](https://vkuzel.com/spring-boot-jpa-hibernate-atomikos-postgresql-exception)
- static methods mocking using PowerMock (logs: BasicStaticClassTest.java uses or overrides a deprecated API.)

### todo

- migrate ~~file-items-service and~~ file-server from mvc to reactive webflux
- migrate from postgres to reactive postgres or some reactive NoSQL (mongodb, etc...)
- add more (advanced) security...
- improve files-db sync (replace FileSystem with GridFS or ...?)
- backup, restore, migration
- support removing files (rly..? as minimum from db)
- p2p: bi-directional files synchronization with spring scheduling or batch

### stack

- spring
  - spring-boot 2.0.2.RELEASE ~~1.x~~
  - spring-data, ~~QueryDSL~~, ~~spring-data-rest,~~ spring-data, jpa
  - cors: see modules/apps/file-items-service/src/main/java/daggerok/config/AppCfg.java
  - 404 fallback: see modules/apps/file-server/src/main/java/daggerok/web/config/FallbackConfig.java
  - ~~spring-social (facebook login required for upload ability)~~ replaced with basic spring-security for now
  - ~~spring annotations (`@Get`, `@Post`, `@WebPage`)~~ (rmeove in order of embedded annotations: `@GetMapping`, `@PostMapping`, etc...)
  - ~~spring-data REST HAL browser~~ (removed)
- code / architecture splitting into micro-services
- migrate postgres from blocking to nonblocking (wrapped in Schedules.elastic())
- migrate REST API from spring mvn to webflux
