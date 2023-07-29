package com.side.cooktime.domain.ingredient.model;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum CountType {
    COUNT("COUNT"), LITER("LITER"), AMOUNT("AMOUNT");

    private String name;

    private static Map<String, CountType> COUNTTYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(CountType::getName, Function.identity())));

    CountType(final String name) {
        this.name = name;
    }

    public static CountType find(String name) {
        return COUNTTYPE_MAP.get(name);
    }
}
