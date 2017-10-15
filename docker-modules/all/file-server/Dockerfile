FROM openjdk:8u131-jre-alpine
MAINTAINER Maksim Kostromin <daggerok@gmail.com>
ENV APP_UPLOAD_PATH="/home/app/file-storage"
RUN apk --no-cache add busybox-suid bash curl \
  && addgroup app-group \
  && adduser -h /home/app -s /bin/bash -D -u 1025 app app-group \
  && mkdir -p /home/app/file-storage
USER app
WORKDIR /home/app
VOLUME ["/home/app/file-storage"]
ENTRYPOINT ["java", "-jar", "./app.jar"]
EXPOSE 8002
HEALTHCHECK --interval=30s --timeout=30s --retries=10 CMD curl -f http://127.0.0.1:8002/health || exit 1
COPY ./application-modules/file-server/build/libs/*.jar ./app.jar
