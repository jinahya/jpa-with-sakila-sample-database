#!/bin/sh
mvn -Pfailsafe,eclipselink clean verify
mvn -Pfailsafe,hibernate clean verify
