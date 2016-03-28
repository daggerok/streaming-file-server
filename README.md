streaming-file-server [![build](https://travis-ci.org/daggerok/streaming-file-server.svg?branch=master)](https://travis-ci.org/daggerok/streaming-file-server)
==============================================================================================================================================================

java file server based on spring mvc and spring-boot with no limitation for upload and download files ..almost :)

```sh
$ git clone ... cd ...
$ gradle clean build bootRun
$ open http://localhost:8080 # enjoy :)
```

h2 console:

```sh
open http://localhost:8080/h2
jdbc url: jdbc:h2:mem:fs
username: fs
password: fs
```

technology stack:

- spring-mvc
- spring-boot
- h2 db, console
- spring-data rest and jpa
- spring-utils, apache fileUpload
- custom spring annotations ```@AliasFor```
- spring-devtools
- lombok
- mustache template engine
- bootstrap-css, bootstrap-filelinput
- gradle
- travis CI

todo:

- common error handling
- support removing files (rly..? as mininum from db)
- improve files-db sync (replace FileSystem with GridFS or ...?)
- bi-directional files synchronization with spring scheduling or batch
- backup, restore, migration
- security, spring-social
- JGiven, sprin mvc testing
