package com.side.cooktime.domain.category.model;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class Categories {

    private List<Category> categories = new ArrayList<>();

    public Categories(List<Category> list){
        categories = list;
    }

    public <T> List<T> toDtos(Function<Category, T> mapper) {
        return categories.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public void remove(Category category){
        categories.remove(category);
    }

    public void add(Category category){
        categories.add(category);
    }
}
