#FROM openjdk:8u191-jre-alpine3.8
##FROM openjdk:8u181-jre-alpine3.8
###FROM openjdk:8u151-jre-alpine3.7
#LABEL MAINTAINER='Maksim Kostromin https://github.com/daggerok'
#
#RUN apk add --no-cache --update bash sudo wget busybox-suid openssh-client  && \
#    adduser -h /home/appuser -s /bin/bash -D -u 1025 appuser wheel          && \
#    echo 'appuser ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers                  && \
#    sed -i 's/.*requiretty$/Defaults !requiretty/' /etc/sudoers             && \
#    apk del --no-cache busybox-suid openssh-client                          && \
#    ( rm -rf /var/cache/apk/* /tmp/* || echo 'oops...' )
#
#USER appuser
#WORKDIR /home/appuser
#VOLUME /home/appuser
#
#ARG JAVA_OPTS_ARGS='\
#-Djava.net.preferIPv4Stack=true \
#-XX:+UnlockExperimentalVMOptions \
#-XX:+UnlockExperimentalVMOptions \
#-XshowSettings:vm '
#ENV JAVA_OPTS="${JAVA_OPTS} ${JAVA_OPTS_ARGS}"
#
#ENTRYPOINT java ${JAVA_OPTS} -jar ./app.jar --spring.profiles.active=db-pg
#CMD /bin/ash
FROM docker_base-streaming-file-server:latest
ENTRYPOINT java ${JAVA_OPTS} -jar ./app.jar --spring.profiles.active=db-pg
EXPOSE 8001
HEALTHCHECK --timeout=2s \
            --retries=22 \
            CMD wget --quiet --tries=1 --spider http://127.0.0.1:8001/actuator/health || exit 1
COPY --chown=appuser ./modules/apps/file-items-service/build/libs/*.jar ./app.jar
