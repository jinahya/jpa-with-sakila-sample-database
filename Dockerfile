FROM mysql/mysql-server:latest AS sakila

ENV MYSQL_ROOT_PASSWORD root
#ENV MYSQL_DATABASE sakila
#ENV MYSQL_USER sakila
#ENV MYSQL_PASSWORD sakila

COPY .docker-entrypoint-initdb.d/01.schema.sql /docker-entrypoint-initdb.d/01.schema.sql
COPY .docker-entrypoint-initdb.d/02.privileges.sql /docker-entrypoint-initdb.d/02.privileges.sql
COPY .docker-entrypoint-initdb.d/03.data.sql /docker-entrypoint-initdb.d/03.data.sql
COPY .docker-entrypoint-initdb.d/04.functions.sql /docker-entrypoint-initdb.d/04.functions.sql
COPY .docker-entrypoint-initdb.d/05.strict.sql /docker-entrypoint-initdb.d/05.strict.sql
COPY .docker-entrypoint-initdb.d/06.user.sql /docker-entrypoint-initdb.d/06.user.sql
COPY .docker-entrypoint-initdb.d/07.user.sql /docker-entrypoint-initdb.d/07.user.sql
COPY .docker-entrypoint-initdb.d/08.user.sql /docker-entrypoint-initdb.d/08.user.sql
COPY .docker-entrypoint-initdb.d/09.user.sql /docker-entrypoint-initdb.d/09.user.sql

EXPOSE 3306/tcp
