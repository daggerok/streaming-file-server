FROM openjdk:8u131-jre-alpine
MAINTAINER Maksim Kostromin <daggerok@gmail.com>
RUN apk --no-cache add busybox-suid bash curl \
  && addgroup app-group \
  && adduser -h /home/app -s /bin/bash -D -u 1025 app app-group
USER app
WORKDIR /home/app
CMD ["java", "-jar", "./app.jar"]
EXPOSE 8001
HEALTHCHECK --interval=10s --timeout=10s --retries=30 CMD curl -f http://127.0.0.1:8001/health || exit 1
COPY ./application-modules/file-items-service/build/libs/*.jar ./app.jar
