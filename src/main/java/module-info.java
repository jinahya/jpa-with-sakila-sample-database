module com.github.jinahya.persistence.sakila {
    requires jakarta.annotation;
    requires jakarta.cdi;
    requires jakarta.el;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires lombok;
    requires org.slf4j;
    opens com.github.jinahya.persistence.sakila;
    exports com.github.jinahya.persistence.sakila;
}
