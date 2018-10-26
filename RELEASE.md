# streaming-file-server

_version: 4.3.4_

## Java file server 

**Java file server** based on *spring-boot* with no memory, upload or download files size limitations

Update version:

- bootstrap 3 to 4 migration:
  - bootstrap -> 4.1.3
  - bootstrap-file-input -> 4.4.8
  - font-awesome -> 5.4.1
  - replaced glyphicons with [Free Font Awesome](https://fontawesome.com/free)
- gradle documentation
  - org.asciidoctor.convert -> 1.5.8.1
  - org.ajoberstar.git-publish -> 2.0.0-rc.2
- upgrade gradle up to 4.10.2
- other versions upgrade:
  - jgiven -> 0.16.1
  - powermock -> 2.0.0-RC.1
  - powermock -> 2.0.0-RC.1
  - selenide -> 5.0.0
  - asciidoctorj-pdf -> 1.5.0-alpha.16
  - com.github.ben-manes gradle plugin -> 0.20.0
  - com.ewerk.gradle.plugins.querydsl -> 1.0.10
  - io.spring.dependency-management -> 1.0.6.RELEASE
  - com.avast.gradle.docker-compose -> 0.7.1

## Installation

### with postgres database

#### manual

```bash
# docker compose file for postgres database
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/docker-compose.yml
docker-compose up -d

# file-items data service
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/file-items-service-4.3.4.jar
java -jar file-items-service-4.3.4.jar --spring.profiles.active=db-pg

# file server
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/file-server-4.3.4.jar
java -jar file-server-4.3.4.jar --app.upload.path=./path/to/file-storage

# cleanup
docker-compose down -v
```

#### or for simplicity use automation shell-script (*nix)

```bash
# bash script
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/application.bash

# start
bash application.bash start ./path/to/file-storage

# stop
bash application.bash stop

# cleanup
bash application.bash clean ./path/to/file-storage
```

*note: tested on osx with docker installed locally*

binaries: `wget`, `docker-compose`, `bash` and of course `java` are required

#### same automation on windows

```cmd
@rem batch shell-script
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/application.cmd

@rem start
application.cmd start path\to\file-storage

@rem stop
application.cmd stop

@rem cleanup
application.cmd clean path\to\file-storage
```

*note: tested on windows 10 with docker installed locally*

binaries: `which`, `wget`, `docker-compose`, `taskkill`, `mkdir` and of course java (binaries: `java` and `jps`) are required

### with h2 in-memory database

#### manual setup

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/file-items-service-4.3.4.jar
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/file-server-4.3.4.jar

bash file-items-service-4.3.4.jar --spring.profiles.active=db-h2
bash file-server-4.3.4.jar --app.upload.path=./path/to/file-storage
```

#### or for simplicity use special h2 automation shell-script

```bash
# bash shell script
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/application-h2.bash

# start
bash application-h2.bash start ./path/to/file-storage

# stop
bash application-h2.bash stop

# cleanup
bash application-h2.bash clean ./path/to/file-storage
```

#### h2 automation for windows

```cmd
@rem cmd script
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.4/application-h2.cmd

@rem start
application-h2.cmd start path\to\file-storage

@rem stop
application-h2.cmd stop

@rem cleanup
application-h2.cmd clean path\to\file-storage
```

*note: tested on windows 10*

binaries: `which`, `del`, `wget`, `taskkill`, `mkdir` and of course `java`, `jps` are required

enjoy :)
