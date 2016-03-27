streaming-file-server [![build](https://travis-ci.org/daggerok/streaming-file-server.svg?branch=master)](https://travis-ci.org/daggerok/streaming-file-server)
==============================================================================================================================================================

java file server based on spring-boot with no limitation for upload and download files ..almost :)


```sh
gradle clean build bootRun
```

stack:

- spring-mvc
- spring-boot
- h2 db, console
- spring-data rest and jpa
- spring-utils, apache fileUpload
- custom spring annotations ```@AliasFor```
- spring-devtools
- lombok
- mustache template engine
- boostrap-css, bootstrap-filelinput
- gradle
- travis CI

todo:

- common error handling
- improve files-db sync (replace FileSystem with GridFS or ...?)
- security, spring-social
- JGiven + mvc testing
- bi-directional files synchronization with spring scheduling or batch
- backup, restore, migration
