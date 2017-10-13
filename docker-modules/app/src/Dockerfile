FROM openjdk:8u131-jre-alpine
MAINTAINER Maksim Kostromin <daggerok@gmail.com>
ENV APP_UPLOAD_PATH="/tmp/file-storage"
RUN mkdir -p /tmp/app /tmp/file-storage \
    && apk --no-cache add curl
WORKDIR /tmp/app
VOLUME ["/tmp/file-storage"]
ENTRYPOINT ["java", "-jar", "./app.jar"]
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=30s --retries=10 CMD curl -f http://127.0.0.1:8080/health || exit 1
COPY ./build/libs/*.jar ./app.jar
