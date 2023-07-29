package com.side.cooktime.domain.ingredient.model;


import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Storage {
    COLD("COLD"), FREEZE("FREEZE"), ROOM("ROOM"),UNKNOWN("UNKNOWN");

    private String name;

    private static Map<String, Storage> STORAGE_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(Storage::getName, Function.identity())));

    Storage(final String name) {
        this.name = name;
    }

    public static Storage find(String name) {
        return STORAGE_MAP.get(name);
    }
}
