package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.jeasy.random.FieldPredicates.named;

class Film_Randomizer
        extends _BaseEntityRandomizer<Film> {

    private static String randomDescription() {
        if (current().nextBoolean()) {
            return null;
        }
        return new StringRandomizer(16).getRandomValue();
    }

    private static Integer randomReleaseYear() {
        if (current().nextBoolean()) {
            return null;
        }
        return current().nextInt(1960, Year.now().getValue());
    }

    private static int randomRentalDuration() {
        return current().nextInt(3, 7);
    }

    private static BigDecimal randomRentalRate() {
        return BigDecimal.valueOf(current().nextDouble(0.9d, 9.9d));
    }

    private static int randomLength() {
        return current().nextInt(30, 240);
    }

    private static double randomReplacementCost() {
        return current().nextDouble(10.99d, 45.99d);
    }

    private static Film.Rating randomRating() {
        if (current().nextBoolean()) {
            return null;
        }
        final var value = Film.Rating.values();
        return value[current().nextInt(0, value.length)];
    }

    private static Set<Film.SpecialFeature> randomSpecialFeatures() {
        if (current().nextBoolean()) {
            return null;
        }
        final List<Film.SpecialFeature> list = new ArrayList<>(EnumSet.allOf(Film.SpecialFeature.class));
        Collections.shuffle(list);
        return IntStream.rangeClosed(0, current().nextInt(list.size()))
                .mapToObj(list::get)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Film.SpecialFeature.class)));
    }

    Film_Randomizer() {
        super(Film.class);
    }

    @Override
    EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Film_.filmId.getName()))
                .randomize(named(Film_.title.getName()), new StringRandomizer(128))
                .excludeField(named(Film_.description.getName()))
                .randomize(named(Film_.releaseYear.getName()), Film_Randomizer::randomReleaseYear)
                .excludeField(named(Film_.languageId.getName()))
                .excludeField(named(Film_.originalLanguageId.getName()))
                .randomize(named(Film_.rentalDuration.getName()), Film_Randomizer::randomRentalDuration)
                .randomize(named(Film_.rentalRate.getName()), Film_Randomizer::randomRentalRate)
                .randomize(named(Film_.length.getName()), Film_Randomizer::randomLength)
                .randomize(named(Film_.replacementCost.getName()), Film_Randomizer::randomReplacementCost)
                .randomize(named(Film_.rating.getName()), Film_Randomizer::randomRating)
                .randomize(named(Film_.specialFeatures.getName()), Film_Randomizer::randomSpecialFeatures)
                .excludeField(named(Film_.language.getName()))
                .excludeField(named(Film_.originalLanguage.getName()))
                ;
    }

    @Override
    EasyRandom random() {
        return super.random();
    }

    @Override
    public Film getRandomValue() {
        return super.getRandomValue();
    }
}
