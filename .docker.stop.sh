#!/bin/sh
. $(dirname $0)/..docker.common.sh
echo stopping...
docker stop "$db"
