# development

**versions maven plugin**

_UNIX: increment current version_

```bash
./mvnw build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} ; ./mvnw -Pversions
```

_Windows: increment current version_

```batch
mvnw build-helper:parse-version versions:set -DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.minorVersion}.${parsedVersion.nextIncrementalVersion}
mvnw -Pversions
```

_process versions substitution_

```bash
./mvnw -Pversions
```
_set specific version_

```bash
./mvnw versions:set -DnewVersion=4.3.31
```

**maven version management**

```bash
./mvnw versions:display-property-updates
```

**run with gradle**

```sh
./gradlew assemble
./gradlew postgresUp
./gradlew :m:a:f-i-s:bootRun
./gradlew :m:a:f-s:bootRun

# cleanup
./gradlew composeDown
./gradlew --stop
```

**gradle testing**

awesome JGiven reports!

```sh
./gradlew clean test jgiven
open `pwd`/modules/apps/file-server/build/jgiven-report/index.html
```

**quick boot all with docker**

```sh
./gradlew clean assemble allUp -Pdebug
http -a user:password :80
./gradlew allDown
```

**run all in docker manually**

```bash
./gradlew clean assemble
#docker-compose -f ./modules/docker/all/docker-compose.yml --project-name=docker up --build --force-recreate
#docker-compose -f ./modules/docker/all/docker-compose.yml -p docker up --build --force-recreate
docker-compose -f ./modules/docker/all/docker-compose.yml up --build --force-recreate
```

**cleanup and remove everything**

```bash
./gradlew composeDown postgresDown allDown clean clear
```

NOTE: if you feel that changes take no effect, clean docker

```bash
# remove containers
for container in $(docker ps -qaf health=healthy) ; do docker rm -v -f $container ; done

# remove volumes
for volume in $(docker volume ls -q) ; do docker volume rm -f $volume ; done

# remove images
for image in $(docker images -qa) ; do docker rmi -f $image ; done

# or cleanup everything in docker
docker system prune -af --volumes
```

**spotbugs vulnerabilities analysis**

```bash
./gradlew check
# or:
./gradlew spotbugsMain spotbugsTest

tree ./build/spotbugs
```

**jacoco code coverage**

```bash
#./gradlew build
# or:
./gradlew check jacocoTestReport jacocoTestCoverageVerification

open ./build/jacoco/modules-apps-file-server/index.html
```

**publish release with maven**

```bash
./mvnw
```

<!--

### known issues (deprecations)

- ~~SQLFeatureNotSupportedException: Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.~~ [fixed](https://vkuzel.com/spring-boot-jpa-hibernate-atomikos-postgresql-exception)
- static methods mocking using PowerMock (logs: BasicStaticClassTest.java uses or overrides a deprecated API.)

-->
