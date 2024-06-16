package com.side.cooktime.domain.ingredient.controller;

import com.side.cooktime.document.RestDocsTestSupport;
import com.side.cooktime.domain.ingredient.model.CountType;
import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.StorageType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IngredientControllerTest extends RestDocsTestSupport {

    @Test
    @DisplayName("save 201")
    public void save_201() throws Exception {
        final Ingredient ingredient = new Ingredient("등심", "testUrl", 30, StorageType.FREEZE, "고기", CountType.AMOUNT);
        when(ingredientService.save(any())).thenReturn(ingredient);

        this.mockMvc.perform(post("/api/v1/ingredient")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .content("{\"name\": \"채소\"}"))
                .andExpect(status().isCreated())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("name").description("재료 대분류 이름")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("ID"),
                                        fieldWithPath("category").description("대분류 이름"),
                                        fieldWithPath("name").description("재료 이름")
                                )
                        ));
    }

    @Test
    @DisplayName("delete 200")
    public void delete_200() throws Exception {
        doNothing().when(ingredientService).delete(1L);
        this.mockMvc.perform(delete("/api/v1/ingredient/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("id").description("카테고리 Id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("삭제된 Id")
                        )
                ));
    }

//    @Test
//    @DisplayName("getIngredients 200")
//    public void getIngredients_200() throws Exception {
//        final Ingredient ingredient = new Ingredient("등심", "testUrl", 30, StorageType.FREEZE, "고기", CountType.AMOUNT);
//        when(ingredientService.save(any())).thenReturn(ingredient);
//
//        this.mockMvc.perform(get("/api/v1/ingredients")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\": \"채소\"}"))
//                .andExpect(status().isOk())
//                .andDo(
//                        restDocs.document(
//                                requestFields(
//                                        fieldWithPath("[].id]").description("재료 id")
//                                ),
//                                responseFields(
//                                        fieldWithPath("[].id").description("재료 id"),
//                                        fieldWithPath("[].type").description("재료 저장 타입"),
//                                        fieldWithPath("[].count").description("재료 갯수"),
//                                        fieldWithPath("[].imageUrl").description("재료 이미지 url"),
//                                        fieldWithPath("[].expirationDate").description("재료 마감 날짜"),
//                                        fieldWithPath("[].name").description("재료 이름")
//                                )
//                        ));
//    }
}
