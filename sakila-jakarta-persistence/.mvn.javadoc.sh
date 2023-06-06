#!/bin/sh
mvn -Ddoclint=none generate-sources compile javadoc:javadoc
if command -v open 2>&1
then
    open target/site/apidocs/index.html
fi
