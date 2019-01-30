#FROM openjdk:8u191-jre-alpine3.8
##FROM openjdk:8u181-jre-alpine3.8
###FROM openjdk:8u151-jre-alpine3.7
#LABEL MAINTAINER='Maksim Kostromin https://github.com/daggerok'
#
#ARG APP_UPLOAD_PATH_ARG='/var/file-storage'
#ENV APP_UPLOAD_PATH="${APP_UPLOAD_PATH_ARG}"
#
#RUN apk add --no-cache --update bash sudo wget busybox-suid openssh-client  && \
#    adduser -h /home/appuser -s /bin/bash -D -u 1025 appuser wheel          && \
#    echo 'appuser ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers                  && \
#    sed -i 's/.*requiretty$/Defaults !requiretty/' /etc/sudoers             && \
#    apk del --no-cache busybox-suid openssh-client                          && \
#    ( rm -rf /var/cache/apk/* /tmp/* || echo 'oops...' )                    && \
#    mkdir -p ${APP_UPLOAD_PATH}                                             && \
#    chown -cR appuser ${APP_UPLOAD_PATH}
#
#USER appuser
#WORKDIR /home/appuser
#VOLUME /home/appuser
#
#ARG JAVA_OPTS_ARGS="\
#-Djava.net.preferIPv4Stack=true \
#-XX:+UnlockExperimentalVMOptions \
#-XX:+UnlockExperimentalVMOptions \
#-XshowSettings:vm "
#ENV JAVA_OPTS="${JAVA_OPTS} ${JAVA_OPTS_ARGS}"
#
#ENTRYPOINT java ${JAVA_OPTS} -jar ./app.jar --spring.profiles.active=db-pg
#CMD /bin/ash
FROM docker_base-streaming-file-server:latest
ENTRYPOINT java ${JAVA_OPTS} -jar ./app.jar --spring.profiles.active=db-pg
EXPOSE 8002
HEALTHCHECK --timeout=2s \
            --retries=22 \
            CMD wget --quiet --tries=1 --spider http://127.0.0.1:8002/actuator/health || exit 1
ARG APP_UPLOAD_PATH_ARG='/var/file-storage'
ENV APP_UPLOAD_PATH="${APP_UPLOAD_PATH_ARG}"
RUN sudo mkdir -p ${APP_UPLOAD_PATH} \
 && sudo chown -cR appuser ${APP_UPLOAD_PATH}
COPY --chown=appuser ./modules/apps/file-server/build/libs/*.jar ./app.jar
