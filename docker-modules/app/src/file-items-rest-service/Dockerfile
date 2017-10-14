FROM openjdk:8u131-jre-alpine
MAINTAINER Maksim Kostromin <daggerok@gmail.com>
RUN mkdir -p /tmp/app \
    && apk --no-cache add curl
WORKDIR /tmp/app
ENTRYPOINT ["java", "-jar", "./app.jar"]
EXPOSE 8001
HEALTHCHECK --interval=30s --timeout=30s --retries=10 CMD curl -f http://127.0.0.1:8001/health || exit 1
COPY ./application-modules/file-items-rest-service/build/libs/*.jar ./app.jar
