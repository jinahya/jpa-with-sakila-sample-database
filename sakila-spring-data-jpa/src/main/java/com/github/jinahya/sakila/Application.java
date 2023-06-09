package com.github.jinahya.sakila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(
        basePackageClasses = {
                com.github.jinahya.persistence.sakila._NoOp.class
        }
)
//@EnableJpaRepositories(
//        basePackageClasses = {
//                com.github.jinahya.sakila.data.jpa.repository._NoOp.class
//        }
//)
@SpringBootApplication
class Application {

    public static void main(final String... args) {
        SpringApplication.run(Application.class, args);
    }
}
