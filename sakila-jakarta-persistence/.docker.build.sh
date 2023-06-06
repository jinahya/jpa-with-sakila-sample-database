#!/bin/sh
. $(dirname $0)/..docker.common.sh
docker image rm -f "$image"
docker build -t "$image" .
