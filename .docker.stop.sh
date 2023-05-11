#!/bin/sh
source $(dirname $0)/..docker.common.sh
echo stopping...
docker stop "$db"
