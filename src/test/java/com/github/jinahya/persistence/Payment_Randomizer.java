package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.math.BigDecimal;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.jeasy.random.FieldPredicates.named;

class Payment_Randomizer
        extends _BaseEntityRandomizer<Payment> {

    private static BigDecimal randomAmount() {
        return BigDecimal.valueOf(current().nextDouble() * current().nextInt(1, 10));
    }

    Payment_Randomizer() {
        super(Payment.class);
    }

    @Override
    protected EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Payment_.paymentId.getName()))
                .excludeField(named(Payment_.customerId.getName()))
                .excludeField(named(Payment_.staffId.getName()))
                .excludeField(named(Payment_.rentalId.getName()))
                .randomize(named(Payment_.amount.getName()), Payment_Randomizer::randomAmount)
                .excludeField(named(Payment_.paymentDate.getName()))
                .excludeField(named(Payment_.customer.getName()))
                .excludeField(named(Payment_.staff.getName()))
                .excludeField(named(Payment_.rental.getName()))
                ;
    }

    @Override
    protected EasyRandom random() {
        return super.random();
    }

    @Override
    public Payment getRandomValue() {
        return super.getRandomValue();
    }
}
