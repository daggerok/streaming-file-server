FROM openjdk:8u151-jre-alpine
MAINTAINER Maksim Kostromin https://github.com/daggerok
ARG APP_UPLOAD_PATH_ARG="/home/appuser/file-storage"
ENV APP_UPLOAD_PATH="${APP_UPLOAD_PATH_ARG}"
RUN apk --no-cache add busybox-suid bash curl sudo \
 && adduser -h /home/appuser -s /bin/bash -D -u 1025 appuser wheel \
 && echo "appuser ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers \
 && sed -i "s/.*requiretty$/Defaults !requiretty/" /etc/sudoers \
 && apk del busybox-suid \
 && rm -rf /tmp/* /var/cache/apk/* \
 && mkdir -p ${APP_UPLOAD_PATH} \
 && chown -R appuser ${APP_UPLOAD_PATH}
USER appuser
WORKDIR /home/appuser
VOLUME /home/appuser
ENTRYPOINT java -XX:+UnlockExperimentalVMOptions \
                -XX:+UseCGroupMemoryLimitForHeap \
                -XshowSettings:vm \
                -jar ./app.jar
CMD /bin/bash
EXPOSE 8002
HEALTHCHECK --timeout=2s \
            --retries=22 \
            CMD curl -f http://127.0.0.1:8002/health || exit 1
COPY --chown=appuser ./application-modules/file-server/build/libs/*.jar ./app.jar
