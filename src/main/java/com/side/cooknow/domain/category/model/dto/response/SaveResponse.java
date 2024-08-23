package com.side.cooknow.domain.category.model.dto.response;

import com.side.cooknow.domain.category.model.Category;
import lombok.Data;

@Data
public class SaveResponse {

    private Long id;
    private String korName;
    private String engName;

    public SaveResponse(Category category) {
        this.id = category.getId();
        this.korName = category.getKorName();
        this.engName = category.getEngName();
    }
}
