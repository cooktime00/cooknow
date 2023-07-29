package com.side.cooktime.domain.category.model.dto.response;

import com.side.cooktime.domain.category.model.Category;
import lombok.Data;

@Data
public class ResponseSaveDto {

    private Long id;
    private String name;

    public ResponseSaveDto(Category category) {
        this.id = category.getId();
        this.name = category.getName().getName();
    }

}
