streaming-file-server [![build](https://travis-ci.org/daggerok/streaming-file-server.svg?branch=master)](https://travis-ci.org/daggerok/streaming-file-server)
=====================

java file server based on spring mvc and spring-boot with no limitation for upload and download files

### Installation:

**with postgres in using docker**

```bash
# database
wget https://github.com/daggerok/streaming-file-server/releases/download/2.1.0/docker-compose-2.1.0.yml
docker-compose -f docker-compose-2.1.0.yml up -d

# application
wget https://github.com/daggerok/streaming-file-server/releases/download/2.1.0/streaming-file-server-2.1.0.jar
mkdir -p ./path/to/file-storage
bash streaming-file-server-2.1.0.jar --app.upload.path=./path/to/file-storage

# cleanup
docker-compose -f docker-compose-2.1.0.yml down -v
```

or simply:

```bash
# start
wget https://github.com/daggerok/streaming-file-server/releases/download/2.1.0/streaming-file-server-2.1.0.bash
bash streaming-file-server-2.1.0.bash start ./path/to/file-storage

# stop
bash streaming-file-server-2.1.0.bash stop

# cleanup
bash streaming-file-server-2.1.0.bash clean ./path/to/file-storage
```

*note: tested on osx, installed binaries: `which`, `rm`, `wget`, `docker-compose`, `kill`, `grep`, `awk`, `mkdir`, `bash` and of caurse `java` are required*

**with h2 in-memory database**

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/2.1.0/streaming-file-server-2.1.0.jar
bash streaming-file-server-2.1.0.jar --spring.profiles.active=db-h2
```

**try it locally**

#### development

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
  - spring-security / spring-social (facebook login required for upload ability)
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

- support removing files (rly..? as minimum from db)
- improve files-db sync (replace FileSystem with GridFS or ...?)
- bi-directional files synchronization with spring scheduling or batch
- backup, restore, migration
