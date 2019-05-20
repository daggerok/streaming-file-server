# docker network create --attachable app
# docker build --no-cache -t file-items-service-app -f linux-services/file-items-service/Dockerfile .
# docker run  -d --rm --net app -p 8002:8002 --name file-items-service file-items-service-app
# docker rm -f -v file-items-service
# docker network rm app
FROM openjdk:11.0.3-jre-stretch
ENTRYPOINT mkdir -p /etc/init.d /var/log/ \
 && touch /tmp/app.jar /var/log/app.log \
 && ln -s /tmp/app.jar /etc/init.d/app \
 && echo ' \
JAVA_OPTS="-Dspring.profiles.active=h2-db -Ddebug=false" \
' > /tmp/app.conf \
 && service app start \
 && tail -f /var/log/app.log
CMD /bin/bash
EXPOSE 8001
HEALTHCHECK --timeout=2s \
            --retries=22 \
            CMD curl -f http://127.0.0.1:8001/actuator/health || exit 1
COPY modules/apps/file-items-service/build/libs/*.jar /tmp/app.jar
