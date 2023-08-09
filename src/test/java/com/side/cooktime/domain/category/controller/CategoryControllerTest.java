package com.side.cooktime.domain.category.controller;

import com.side.cooktime.document.RestDocsTestSupport;
import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.category.model.Ingredients;
import com.side.cooktime.domain.ingredient.model.Ingredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends RestDocsTestSupport {

    @Test
    @DisplayName("Save 201")
    public void save_201() throws Exception {
        final Category category = new Category(1L,"채소");
        when(categoryService.save(any())).thenReturn(category);

        this.mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"채소\"}"))
                .andExpect(status().isCreated())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("name").description("재료 대분류 이름").optional()
                                ),
                                responseFields(
                                        fieldWithPath("id").description("ID"),
                                        fieldWithPath("name").description("재료 대분류 이름")
                                )
                        ));
    }

    @Test
    @DisplayName("Delete 200")
    public void delete_200() throws Exception {
        doNothing().when(categoryService).delete(1L);
        this.mockMvc.perform(delete("/api/v1/category/{id}", 1L))
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

    @Test
    @DisplayName("findIngredients 200")
    public void findIngredients_200() throws Exception {
        Long categoryId = 1L;
        Category category = new Category(categoryId, "고기");

        Ingredient ingredient1 = new Ingredient(1L, "안심", "testUrl", category);
        Ingredient ingredient2 = new Ingredient(2L, "등심", "testUrl", category);
        Ingredients ingredients = new Ingredients(Arrays.asList(ingredient1, ingredient2));

        when(categoryService.getIngredients(1L)).thenReturn(ingredients);

        this.mockMvc.perform(get("/api/v1/category/{id}/ingredients", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("카테고리 Id")
                                ),
                                responseFields(
                                        fieldWithPath("[].ingredientId").description("재료 Id"),
                                        fieldWithPath("[].categoryId").description("카테고리 Id"),
                                        fieldWithPath("[].name").description("재료 이름"),
                                        fieldWithPath("[].imageUrl").description("이미지 URL")
                                )
                        ));
    }
}