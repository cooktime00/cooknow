package com.side.cooktime.domain.ingredient.model.dto.request;

import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.ingredient.model.*;
import lombok.Data;

@Data
public class RequestSaveDto {

    private Long categoryId;
    private String name;
    private String url;
    private int expirationPeriod;
    private String storage;
    private String countType;

    public Ingredient toEntity(Category category){
        return Ingredient.builder()
                .category(category)
                .name(new Name(name))
                .image(new Image(url))
                .expirationPeriod(new Day(expirationPeriod))
                .storage(Storage.find(storage))
                .countType(CountType.find(countType))
                .build();
    }


}
