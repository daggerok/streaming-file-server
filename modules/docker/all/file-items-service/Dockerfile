FROM openjdk:8u181-jre-alpine3.8
#FROM openjdk:8u151-jre-alpine3.7
LABEL MAINTAINER='Maksim Kostromin https://github.com/daggerok'

RUN apk --no-cache --update add busybox-suid bash unzip sudo openssh-client shadow wget      && \
    adduser -h /home/appuser -s /bin/bash -D -u 1025 appuser wheel                           && \
    echo "appuser ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers                                   && \
    sed -i "s/.*requiretty$/Defaults !requiretty/" /etc/sudoers                              && \
    wget --no-cookies --no-check-certificate -O /tmp/jce_policy-8.zip                           \
         --header "Cookie: oraclelicense=accept-securebackup-cookie"                            \
                  "http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip"           && \
    unzip -o /tmp/jce_policy-8.zip -d /tmp                                                   && \
    mv -f ${JAVA_HOME}/lib/security ${JAVA_HOME}/lib/backup-security || echo 'cannot backup' && \
    mv -f /tmp/UnlimitedJCEPolicyJDK8 ${JAVA_HOME}/lib/security                              && \
    apk del busybox-suid unzip openssh-client shadow wget                                    && \
    rm -rf /var/cache/apk/* /tmp/*

USER appuser
WORKDIR /home/appuser
VOLUME /home/appuser

ARG JAVA_OPTS_ARGS="\
-Djava.net.preferIPv4Stack=true \
-XX:+UnlockExperimentalVMOptions \
-XX:+UnlockExperimentalVMOptions \
-XshowSettings:vm "
ENV JAVA_OPTS="${JAVA_OPTS} ${JAVA_OPTS_ARGS}"

ENTRYPOINT java ${JAVA_OPTS} -jar ./app.jar --spring.profiles.active=db-pg
CMD /bin/bash

EXPOSE 8001
HEALTHCHECK --timeout=2s \
            --retries=22 \
            CMD wget --quiet --tries=1 --spider http://127.0.0.1:8001/actuator/health || exit 1

COPY --chown=appuser ./modules/apps/file-items-service/build/libs/*.jar ./app.jar
