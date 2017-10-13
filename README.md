streaming-file-server [![build](https://travis-ci.org/daggerok/streaming-file-server.svg?branch=master)](https://travis-ci.org/daggerok/streaming-file-server)
=====================

fullstack java file server based on spring-boot / spring-* with no limitation for upload and download files

[**try it locally**](https://github.com/daggerok/streaming-file-server/releases)

### Installation:

**with postgres in using docker**

```bash
export VERSION="2.1.2"

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

*note: tested on osx with localhost docker.
installed binaries: `which`, `rm`, `wget`, `docker-compose`, `kill`, `grep`, `awk`, `mkdir`, `bash` and of caurse `java` are required*

for windows use: https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/streaming-file-server-$VERSION.cmd

```cmd
@rem start
streaming-file-server-$VERSION.cmd start path\to\file-storage

@rem stop
streaming-file-server-$VERSION.cmd stop

@rem cleanup
streaming-file-server-$VERSION.cmd clean path\to\file-storage
```

*note: tested on windows 10 with localhost docker for postgres.
installed binaries: `which`, `del`, `wget`, `docker-compose`, `taskkill`, `mkdir` and of caurse `java`, `jps` are required*

**with h2 in-memory database**

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/streaming-file-server-$VERSION.jar
bash streaming-file-server-$VERSION.jar --spring.profiles.active=db-h2
```

or simply for h2:

```bash
# start
wget https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/streaming-file-server-h2-$VERSION.bash
bash streaming-file-server-h2-$VERSION.bash start ./path/to/file-storage

# stop
bash streaming-file-server-h2-$VERSION.bash stop

# cleanup
bash streaming-file-server-h2-$VERSION.bash clean ./path/to/file-storage
```

for windows use: https://github.com/daggerok/streaming-file-server/releases/download/$VERSION/streaming-file-server-h2-$VERSION.cmd

```cmd
@rem start
streaming-file-server-h2-$VERSION.cmd start path\to\file-storage

@rem stop
streaming-file-server-h2-$VERSION.cmd stop

@rem cleanup
streaming-file-server-h2-$VERSION.cmd clean path\to\file-storage
```

*note: tested on windows 10.
installed binaries: `which`, `del`, `wget`, `taskkill`, `mkdir` and of caurse `java`, `jps` are required*

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

befor start, install all what you needed:
 
- java [from here](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or [from here](https://java.com/ru/download/)
- if you on windows: [powershell](https://www.microsoft.com/en-us/download/details.aspx?id=34595) and [scoop](https://github.com/lukesampson/scoop)
