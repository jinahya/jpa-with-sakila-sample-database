#!/bin/sh
source $(dirname $0)/..docker.common.sh
sh ./.docker.stop.sh
echo sleeping...
sleep 5
echo running...
docker run -e LANG=C.UTF-8 --rm -d --name "$db" -p "$port":3306 "$image"
