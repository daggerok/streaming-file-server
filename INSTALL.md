# Installation

[**try it locally**](https://github.com/daggerok/streaming-file-server/releases)

## Scripts usage

**available commands for \*nix systems scripts**

```bash
./application> start path/to/storage
./application> stop
./application> clean path/to/storage
```

**available commands fir windows scripts**

```cmd
application.cmd start path\to\storage
application.cmd stop
application.cmd clean path\to\storage
```

## Other options in details

**with postgres in docker**

```bash
# database
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/docker-compose.yml
docker-compose -f docker-compose.yml up -d

# file-items data service
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/file-items-service-4.3.26.jar
bash file-items-service-4.3.26.jar --spring.profiles.active=db-pg

# file server
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/file-server-4.3.26.jar
bash file-server-4.3.26.jar --app.upload.path=./path/to/file-storage

# cleanup
docker-compose -f docker-compose.yml down -v
```

**or simply using shell-script**

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/application.bash

# start
bash application.bash start ./path/to/file-storage

# stop
bash application.bash stop

# cleanup
bash application.bash clean ./path/to/file-storage
```

*note: tested on osx with localhost docker*

installed binaries: `wget`, `docker-compose`, `bash` and of course `java` are required

**for windows use https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/application.cmd**

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
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/file-items-service-4.3.26.jar
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/file-server-4.3.26.jar

# bash file-items-service-4.3.26.jar --spring.profiles.active=db-h2 # or just:
bash file-items-service-4.3.26.jar
bash file-server-4.3.26.jar --app.upload.path=./path/to/file-storage
```

**or simply shell script for h2**

```bash
wget https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/application-h2.bash

# start
bash application-h2.bash start ./path/to/file-storage

# stop
bash application-h2.bash stop

# cleanup
bash application-h2.bash clean ./path/to/file-storage
```

**for windows use https://github.com/daggerok/streaming-file-server/releases/download/4.3.26/application-h2.cmd**

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
