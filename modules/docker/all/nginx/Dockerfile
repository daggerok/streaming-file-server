FROM nginx:1.17.0-alpine
#FROM nginx:1.15.12-alpine
LABEL MAINTAINER='Maksim Kostromin https://github.com/daggerok'
HEALTHCHECK --retries=33 \
            --timeout=1s \
            --interval=3s \
            --start-period=1s \
            CMD true
ADD ./default.conf /etc/nginx/conf.d/default.conf
