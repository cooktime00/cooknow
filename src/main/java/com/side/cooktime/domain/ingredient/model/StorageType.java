package com.side.cooktime.domain.ingredient.model;


import com.side.cooktime.controller.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum StorageType implements EnumType {
    COLD("COLD"),
    FREEZE("FREEZE"),
    ROOM("ROOM"),
    UNKNOWN("UNKNOWN");

    private final String description;

    private static Map<String, StorageType> STORAGE_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(StorageType::getDescription, Function.identity())));

    @Override
    public String getName(){
        return this.name();
    }

    public static StorageType find(String name) {
        return STORAGE_MAP.get(name);
    }
}
