package com.side.cooknow.domain.ingredient.model.dto.request;

import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.ingredient.model.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RequestSaveDto {

    private Long categoryId;
    private String korName;
    private String engName;
    private MultipartFile url;
    private int expirationPeriod;
    private String storage;
    private String countType;

    public Ingredient toEntity(Category category, String s3ImageUrl){
        return Ingredient.builder()
                .category(category)
                .korName(new Name(korName))
                .engName(new Name(engName))
                .image(new Image(s3ImageUrl))
                .expirationPeriod(new Day(expirationPeriod))
                .storageType(StorageType.find(storage))
                .countType(CountType.find(countType))
                .build();
    }
}
