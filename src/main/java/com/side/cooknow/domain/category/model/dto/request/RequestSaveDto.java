package com.side.cooknow.domain.category.model.dto.request;

import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.ingredient.model.Name;
import lombok.Data;

@Data
public class RequestSaveDto {

    private String name;

    public RequestSaveDto() {

    }

    public Category toEntity() {
        return Category.builder()
                .korName(new Name(name))
                .build();
    }

}
