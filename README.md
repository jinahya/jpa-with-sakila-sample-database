# jpa-with-sakila-sample-database

Defines entity classes for [Sakila Sample Database](https://dev.mysql.com/doc/sakila/en/).

## Docker

### How to run Sakila database with Docker

#### [docker build](https://docs.docker.com/engine/reference/commandline/build/)

```shell
$ sh ./.docker.build.sh
```

#### [docker run](https://docs.docker.com/engine/reference/commandline/run/)

```shell
$ sh ./.docker.run.sh
```

#### [docker exec](https://docs.docker.com/engine/reference/commandline/exec/)

```shell
$ sh ./.docker.connect.sh
```

#### [docker stop](https://docs.docker.com/engine/reference/commandline/stop/)

```shell
$ sh ./.docker.stop.sh
```

----

## Links

----

### SQL

* [How to Select the First/Least/Max Row per Group in SQL](https://www.xaprb.com/blog/2006/12/07/how-to-select-the-firstleastmax-row-per-group-in-sql/)

----

### MySQL

* MySQL Reference Manual
    * [11.4.1 Spatial Data types](https://dev.mysql.com/doc/refman/8.0/en/spatial-type-overview.html)
    * [11.4.3 Supported Spatial Data Formats](https://dev.mysql.com/doc/refman/8.0/en/gis-data-formats.html)
* Miscellaneous
    * [How "YEAR" data type take only 1 byte in mysql server](https://stackoverflow.com/q/23074989/330457) (
      StackOverflow.com)

#### Sakila Sample Database

* [Sakila Sample Database](https://dev.mysql.com/doc/sakila/en/)
    * [5 Structure](https://dev.mysql.com/doc/sakila/en/sakila-structure.html)
* [Motion Picture Association film rating system](https://en.wikipedia.org/wiki/Motion_Picture_Association_film_rating_system) (
  Wikipedia.com)

----

### [Jakarta Bean Validation](https://beanvalidation.org/)

#### [Jakarta Bean Validation 3.0](https://beanvalidation.org/3.0/)

#### [Hibernate Validator](https://hibernate.org/validator/)

* [Cascaded return value validation causes exception when used with Weld](https://hibernate.atlassian.net/browse/HV-770)

----

### [Jakarta Persistence](https://jakarta.ee/specifications/persistence/)

#### [Jakarta Persistence 3.1](https://jakarta.ee/specifications/persistence/3.1/)

* [2.1. The Entity Class](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#a18)
* [2.4. Primary Keys and Entity Identity](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html#a132)

#### [EclipseLink JPA](https://www.eclipse.org/eclipselink/#jpa)

* [UserGuide/JPA/Using the Canonical Model Generator (ELUG)](https://wiki.eclipse.org/UserGuide/JPA/Using_the_Canonical_Model_Generator_(ELUG))
* [Getting a JDBC Connection from an EntityManager](https://wiki.eclipse.org/EclipseLink/Examples/JPA/EMAPI#Getting_a_JDBC_Connection_from_an_EntityManager)
* [`@ReadOnly`](https://javadoc.io/doc/org.eclipse.persistence/eclipselink/latest/eclipselink/org/eclipse/persistence/annotations/ReadOnly.html)

#### [Hibernate ORM](https://hibernate.org/orm/)

* [`@Immutable`](https://javadoc.io/doc/org.hibernate/hibernate-core/5.6.15.Final/org/hibernate/annotations/Immutable.html)

----

### Geospatial

#### GEOS

* [Well-Known Binary (WKB)](https://libgeos.org/specifications/wkb/#standard-wkb)

#### Open Geospatial Consortium

* [Simple feature access - Part 1 - OGC Portal](https://portal.ogc.org/files/?artifact_id=25355)

----

### bugs.oepnjdk.org

* [maven javadoc shows annotation twice](https://stackoverflow.com/q/64045629/330457)
    * [Documented annotations show up twice in Javadoc for fields and parameters](https://bugs.openjdk.org/browse/JDK-8175533)

----

### Miscellaneous

* [Random Address](https://www.bestrandoms.com/random-address)
    * [in United States](https://www.bestrandoms.com/random-address-in-us)
    * [in Korean](https://www.bestrandoms.com/random-address-in-ko)
* [JPA Metamodel generation missing attributes](https://stackoverflow.com/q/27333779/330457)
    * [`@Basic`](https://stackoverflow.com/a/41720695/330457)
