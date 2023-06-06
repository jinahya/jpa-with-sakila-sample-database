#!/bin/sh
mvn -Peclipselink clean test
mvn -Phibernate clean test
