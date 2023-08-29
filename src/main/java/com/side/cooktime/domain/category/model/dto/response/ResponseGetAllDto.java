package com.side.cooktime.domain.category.model.dto.response;

import com.side.cooktime.domain.category.model.Category;
import lombok.Data;

@Data
public class ResponseGetAllDto {

    private Long id;
    private String name;

    public ResponseGetAllDto(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
