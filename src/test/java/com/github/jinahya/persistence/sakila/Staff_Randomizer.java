package com.github.jinahya.persistence.sakila;

import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.util.concurrent.ThreadLocalRandom;

import static org.jeasy.random.FieldPredicates.named;

@Slf4j
class Staff_Randomizer
        extends _BaseEntityRandomizer<Staff> {

    static Integer randomActive() {
        return ThreadLocalRandom.current().nextBoolean() ? 0 : 1;
    }

    static byte[] randomClientPassword() {
        final byte[] clientPassword = new byte[ThreadLocalRandom.current().nextInt(8, 16)];
        ThreadLocalRandom.current().nextBytes(clientPassword);
        return clientPassword;
    }

    Staff_Randomizer() {
        super(Staff.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Staff_.staffId.getName()))
                .randomize(named(Staff_.firstName.getName()), new StringRandomizer(25))
                .randomize(named(Staff_.lastName.getName()), new StringRandomizer(25))
                .excludeField(named(Staff_.addressId.getName()))
                .excludeField(named(Staff_.picture.getName()))
                .excludeField(named(Staff_.email.getName()))
                .excludeField(named(Staff_.storeId.getName()))
                .randomize(named(Staff_.active.getName()), Staff_Randomizer::randomActive)
                .randomize(named(Staff_.username.getName()), new StringRandomizer(16))
                .excludeField(named(Staff_.password.getName()))
                .excludeField(named(Staff_.address.getName()))
                .excludeField(named(Staff_.store.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Staff getRandomValue() {
        final var value = super.getRandomValue();
        if (ThreadLocalRandom.current().nextBoolean()) {
            value.changePassword(null, randomClientPassword());
        }
        return value;
    }
}
