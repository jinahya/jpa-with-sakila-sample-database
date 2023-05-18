package com.github.jinahya.persistence;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.jeasy.random.FieldPredicates.named;

class Language_Randomizer
        extends _BaseEntityRandomizer<Language> {

    static String randomLanguage() {
        final List<Locale> list = Arrays.asList(Locale.getAvailableLocales());
        Collections.shuffle(list);
        return list.stream()
                .filter(l -> !l.getDisplayLanguage(Locale.ENGLISH).isBlank())
                .findFirst()
                .map(l -> l.getDisplayLanguage(Locale.ENGLISH))
                .orElseThrow(() -> new RuntimeException("no locale with non-blank display language"));
    }

    Language_Randomizer() {
        super(Language.class);
    }

    @Override
    protected EasyRandomParameters parameters() {
        return super.parameters()
                .excludeField(named(Language_.languageId.getName()))
                .randomize(named(Language_.name.getName()), Language_Randomizer::randomLanguage)
                ;
    }

    @Override
    protected EasyRandom random() {
        return super.random();
    }

    @Override
    public Language getRandomValue() {
        return super.getRandomValue();
    }
}
