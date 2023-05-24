package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.util.concurrent.ThreadLocalRandom;

import static org.jeasy.random.FieldPredicates.named;

class Customer_Randomizer
        extends _BaseEntityRandomizer<Customer> {

    static int randomActive() {
        return ThreadLocalRandom.current().nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE + 1);
    }

    Customer_Randomizer() {
        super(Customer.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Customer_.customerId.getName()))
                .excludeField(named(Customer_.storeId.getName()))
                .randomize(named(Customer_.firstName.getName()), new StringRandomizer(45))
                .randomize(named(Customer_.lastName.getName()), new StringRandomizer(45))
                .excludeField(named(Customer_.email.getName()))
                .excludeField(named(Customer_.addressId.getName()))
                .randomize(named(Customer_.active.getName()), Customer_Randomizer::randomActive)
                .excludeField(named(Customer_.createDate.getName()))
                .excludeField(named(Customer_.store.getName()))
                .excludeField(named(Customer_.address.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Customer getRandomValue() {
        return super.getRandomValue();
    }
}
