package com.github.jinahya.persistence.sakila;

import jakarta.inject.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A qualifier annotation for beans whose {@code close()} method is prohibited.
 *
 * @author Jin Kwon &lt;onacit_at_gmail.com&gt;
 */
@Qualifier
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ___Uncloseable {

}
