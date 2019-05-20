# docker network create --attachable app
# docker build --no-cache -t file-server-app -f linux-services/file-server/Dockerfile .
# docker run  -d --rm --net app -p 8002:8002 --name file-server file-server-app
# docker rm -f -v file-server
# docker network rm app
FROM openjdk:11.0.3-jre-stretch
ENTRYPOINT mkdir -p /etc/init.d /var/log/ \
 && touch /tmp/app.jar /var/log/app.log \
 && ln -s /tmp/app.jar /etc/init.d/app \
 && echo ' \
JAVA_OPTS="-Dfile-items-rest-service.host=file-items-service -Dfile-items-rest-service.port=8001 -Ddebug=false" \
' > /tmp/app.conf \
 && service app start \
 && tail -f /var/log/app.log
CMD /bin/bash
EXPOSE 8002
HEALTHCHECK --timeout=2s \
            --retries=22 \
            CMD curl -f http://127.0.0.1:8002/actuator/health || exit 1
COPY modules/apps/file-server/build/libs/*.jar /tmp/app.jar
