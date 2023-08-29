package com.side.cooktime.domain.ingredient.model;

import com.side.cooktime.global.model.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CountType implements EnumType {
    COUNT("COUNT"), LITER("LITER"), AMOUNT("AMOUNT");

    private final String description;

    private static Map<String, CountType> COUNTTYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(CountType::getDescription, Function.identity())));

    @Override
    public String getName() {
        return this.name();
    }

    public static CountType find(String name) {
        return COUNTTYPE_MAP.get(name);
    }
}
