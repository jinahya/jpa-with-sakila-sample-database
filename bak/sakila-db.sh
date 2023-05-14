#!/bin/sh
tar xzf sakila-db.tar.gz
cp sakila-db/sakila-schema.sql ../.docker-entrypoint-initdb.d/01.schema.sql
cp sakila-db/sakila-data.sql ../.docker-entrypoint-initdb.d/03.data.sql
