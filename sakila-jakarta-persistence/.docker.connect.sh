#!/bin/sh
### docker exec -it db1 mysql -uuser1 -ppass1 -Ddb1
. $(dirname $0)/..docker.common.sh
docker exec -it "$container" mysql -u"$user" -p"$password" -D"$db"
