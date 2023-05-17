package com.github.jinahya.persistence;

import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.util.HexFormat;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.named;

@Slf4j
class AddressRandomizer
        extends _BaseEntityRandomizer<Address> {

    AddressRandomizer() {
        super(Address.class);
    }

    @Override
    protected EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Address_.addressId.getName()))
                .randomize(named(Address_.address.getName()), new StringRandomizer(50))
                .randomize(named(Address_.address2.getName()), new StringRandomizer(50))
                .randomize(named(Address_.district.getName()), new StringRandomizer(20))
                .excludeField(named(Address_.cityId.getName()))
                .randomize(named(Address_.postalCode.getName()), new StringRandomizer(10))
                .randomize(named(Address_.phone.getName()), new StringRandomizer(10))
                .excludeField(named("locationGeometry"))
                ;
    }

    @Override
    protected EasyRandom random() {
        return super.random();
    }

    @Override
    public Address getRandomValue() {
        final var address = super.getRandomValue();
        address.setLocation(HexFormat.of().parseHex("000000000101000000000000000000F03F000000000000F0BF"));
        address.setLocationGeometryAsPoint(current().nextDouble(), current().nextDouble());
        assertThat(address.getLocation()).hasSize(25);
        log.debug("location: {}", HexFormat.of().withUpperCase().formatHex(address.getLocation()));
        return address;
    }
}
