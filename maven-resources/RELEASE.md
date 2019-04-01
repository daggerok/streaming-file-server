# streaming-file-server
_version: ${project.version}_

## Java file server 

**Java file server** based on *spring-boot* with no memory, upload or download files size limitations

- introduce Gradle Kotlin DSL API
- versions upgrade:

  |                                                                    dependency | version       |
  |------------------------------------------------------------------------------:|:--------------|
  |                                                                        gradle | 5.3.1         |
  |                                                        com.codeborne:selenide | 5.2.2         |
  |     com.github.ben-manes.versions:com.github.ben-manes.versions.gradle.plugin | 0.21.0        |
  |                                                           com.github.spotbugs | 3.1.12        |
  |                         com.github.spotbugs:com.github.spotbugs.gradle.plugin | 1.7.1         |
  | io.spring.dependency-management:io.spring.dependency-management.gradle.plugin | 1.0.7.RELEASE |
  |                                                 org.hibernate:hibernate-java8 | 5.4.2.Final   |
  |                                                      org.webjars:font-awesome | 5.8.1         |
  |                                        org.webjars.bower:bootstrap-fileinpute | 4.5.2         |

### incompatible (not upgradeable) versions

    ch.qos.logback:logback-classic [1.2.3 -> 1.3.0-alpha4]
    com.avast.gradle.docker-compose:com.avast.gradle.docker-compose.gradle.plugin [0.7.1 -> 0.8.14]
    io.vavr:vavr [0.9.2 -> 1.0.0-alpha-2]
    org.asciidoctor.convert:org.asciidoctor.convert.gradle.plugin [1.5.9.1 -> 2.0-alpha.5]
    org.jruby:jruby-complete [9.2.5.0 -> 9.2.6.0]

## Installation

### with postgres database

#### manual

```bash
# docker compose file for postgres database
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/docker-compose.yml
docker-compose up -d

# file-items data service
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/file-items-service-${project.version}.jar
java -jar file-items-service-${project.version}.jar --spring.profiles.active=db-pg

# file server
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/file-server-${project.version}.jar
java -jar file-server-${project.version}.jar --app.upload.path=./path/to/file-storage

# cleanup
docker-compose down -v
```

#### or for simplicity use automation shell-script (*nix)

```bash
# bash script
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/application.bash

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
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/application.cmd

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
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/file-items-service-${project.version}.jar
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/file-server-${project.version}.jar

bash file-items-service-${project.version}.jar --spring.profiles.active=db-h2
bash file-server-${project.version}.jar --app.upload.path=./path/to/file-storage
```

#### or for simplicity use special h2 automation shell-script

```bash
# bash shell script
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/application-h2.bash

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
wget https://github.com/daggerok/streaming-file-server/releases/download/${project.version}/application-h2.cmd

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
