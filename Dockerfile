FROM mysql/mysql-server:latest AS db1

ENV MYSQL_ROOT_PASSWORD root
#ENV MYSQL_DATABASE sakila
#ENV MYSQL_USER sakila
#ENV MYSQL_PASSWORD sakila

COPY docker-entrypoint-initdb.d/01.schema.sql /docker-entrypoint-initdb.d/01.schema.sql
COPY docker-entrypoint-initdb.d/02.user.sql /docker-entrypoint-initdb.d/02.user.sql
COPY docker-entrypoint-initdb.d/03.data.sql /docker-entrypoint-initdb.d/03.data.sql

EXPOSE 3306/tcp
