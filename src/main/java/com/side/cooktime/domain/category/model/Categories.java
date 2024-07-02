package com.side.cooktime.domain.category.model;

import com.side.cooktime.domain.category.model.dto.response.ResponseFindIngredientsDto;
import com.side.cooktime.domain.category.model.dto.response.ResponseGetAllWithIngredientsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
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

    public List<ResponseGetAllWithIngredientsDto> toDtosResponseGetAllWithIngredientsDto(Locale requestLocale) {
        return categories.stream()
                .map(category -> {
                    List<ResponseFindIngredientsDto> ingredientsDto = category.getIngredients().toDtos(ResponseFindIngredientsDto::new, requestLocale);
                    return new ResponseGetAllWithIngredientsDto(category, ingredientsDto, requestLocale);
                })
                .collect(Collectors.toList());
    }
}
