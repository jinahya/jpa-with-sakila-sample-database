#!/bin/sh
mvn -Peclipselink help:effective-pom -Doutput=effective-pom-eclipselink.xml
mvn -Phibernate help:effective-pom -Doutput=effective-pom-hibernate.xml
