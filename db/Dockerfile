FROM mysql:8.0

COPY ./dailyhubdata.sql /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD=1234
ENV MYSQL_DATABASE=dailyhub
ENV MYSQL_HOST=%