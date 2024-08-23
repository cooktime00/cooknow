package com.side.cooknow.domain.category.model.dto.request;

import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.ingredient.model.Name;
import lombok.Data;

@Data
public class RequestSaveDto {

    private String korName;
    private String engName;

    public RequestSaveDto() {

    }

    public Category toEntity() {
        return Category.builder()
                .korName(new Name(korName))
                .engName(new Name(engName))
                .build();
    }

}
