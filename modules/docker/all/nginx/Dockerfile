FROM nginx:1.15.5-alpine
LABEL MAINTAINER='Maksim Kostromin https://github.com/daggerok'
HEALTHCHECK --timeout=2s \
            --retries=22 \
            CMD wget --quiet --tries=1 --spider http://127.0.0.1:80/actuator/health || exit 1
ADD ./default.conf /etc/nginx/conf.d/default.conf
