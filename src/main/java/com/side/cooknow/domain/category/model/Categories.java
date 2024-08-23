package com.side.cooknow.domain.category.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class Categories {

    private List<Category> categories = new ArrayList<>();

    public Categories(List<Category> list) {
        categories = list;
    }

    public <T> List<T> toDtos(BiFunction<Category, Locale, T> mapper, Locale locale) {
        return categories.stream()
                .map(category -> mapper.apply(category, locale))
                .collect(Collectors.toList());
    }

    public void remove(Category category) {
        categories.remove(category);
    }

    public void add(Category category) {
        categories.add(category);
    }

    

}
