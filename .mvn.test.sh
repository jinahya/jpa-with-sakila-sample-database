#!/bin/sh
mvn -Psp-eclipselink clean test
mvn -Psp-hibernate clean test
