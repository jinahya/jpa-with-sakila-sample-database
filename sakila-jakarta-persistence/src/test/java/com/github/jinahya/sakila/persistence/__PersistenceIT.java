package com.github.jinahya.sakila.persistence;

import com.github.jinahya.sakila.persistence.util.PersistenceUtils;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@AddPackages({___EntityManagerProducer.class})
@EnableAutoWeld
public class __PersistenceIT {

    /**
     * Applies an <em>injected</em> instance of {@link EntityManager} to specified function, and returns the result.
     *
     * @param function the function.
     * @param <R>      result type parameter
     * @return the result of the {@code function}.
     * @implNote Note that all operations, done with the applied entity manager, are rolled back at the end.
     * @see #acceptEntityManager(Consumer)
     */
    protected <R> R applyEntityManager(final Function<? super EntityManager, ? extends R> function) {
        Objects.requireNonNull(function, "function is null");
        return PersistenceUtils.applyEntityManagerInTransactionAndRollback(entityManager, function);
    }

    /**
     * Accepts an <em>injected</em> instance of {@link EntityManager} to specified consumer.
     *
     * @param consumer the consumer.
     * @implNote Note that all operations, done with the accepted entity manager, are rolled back at the end.
     * @see #applyEntityManager(Function)
     */
    protected void acceptEntityManager(final Consumer<? super EntityManager> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");
        applyEntityManager(em -> {
            consumer.accept(em);
            return null;
        });
    }

    @___Uncloseable // clients may not invoke close() method on this object!
    @Inject
    private EntityManager entityManager;
}
