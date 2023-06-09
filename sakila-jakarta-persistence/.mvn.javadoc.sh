#!/bin/sh
mvn -Ddoclint=none generate-sources compile generate-test-sources test-compile javadoc:javadoc javadoc:test-javadoc
if command -v open 2>&1a
then
    open target/site/apidocs/index.html
fi
