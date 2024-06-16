package com.side.cooktime.domain.category.model.dto.response;

import com.side.cooktime.domain.category.model.Category;
import lombok.Data;

import java.util.List;
import java.util.Locale;

@Data
public class ResponseGetAllWithIngredientsDto {

    private Long id;
    private String name;
    private List<ResponseFindIngredientsDto> ingredients;

    public ResponseGetAllWithIngredientsDto(Category category, List<ResponseFindIngredientsDto> ingredients, Locale locale) {
        this.id = category.getId();
        this.name = getNameByLocale(category, locale);
        this.ingredients = ingredients;
    }

    private String getNameByLocale(Category category, Locale locale) {
        if (locale.getLanguage().equals("en")) {
            return category.getEngName();
        }
        return category.getKorName();
    }
}
