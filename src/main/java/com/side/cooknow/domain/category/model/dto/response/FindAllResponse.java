package com.side.cooknow.domain.category.model.dto.response;

import com.side.cooknow.domain.category.model.Category;
import lombok.Data;

import java.util.Locale;

@Data
public class FindAllResponse {

    private Long id;
    private String name;

    public FindAllResponse(Category category, Locale locale) {
        this.id = category.getId();
        this.name = getNameByLocale(category, locale);
    }

    private String getNameByLocale(Category category, Locale locale) {
        if (locale.getLanguage().equals("en")) {
            return category.getEngName();
        }
        return category.getKorName();
    }
}
