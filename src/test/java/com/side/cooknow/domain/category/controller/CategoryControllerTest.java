package com.side.cooknow.domain.category.controller;

import com.side.cooknow.document.RestDocsTestSupport;
import com.side.cooknow.domain.category.model.Categories;
import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.ingredient.model.Ingredients;
import com.side.cooknow.domain.ingredient.model.Ingredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Locale;

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

    private Locale[] locales = {Locale.KOREAN, Locale.ENGLISH};

    @Test
    @DisplayName("Save 201")
    public void save_201() throws Exception {
        final Category category = new Category(1L, "채소");
        when(categoryService.save(any())).thenReturn(category);
        doNothing().when(jwtTokenService).verifyAccessToken(any());

        this.mockMvc.perform(post("/api/v1/category")
                        .header("Authorization", "Bearer AccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"채소\"}"))
                .andExpect(status().isCreated())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("name").description("재료 대분류 이름").optional()
                                ),
                                responseFields(
                                        fieldWithPath("size").description("Save 된 갯수")
                                )
                        ));
    }

    @Test
    @DisplayName("getAll 200")
    public void getAll_200() throws Exception {

        Category category1 = new Category(1L, "고기");
        Category category2 = new Category(2L, "채소");
        Category category3 = new Category(3L, "해산물");
        Category category4 = new Category(4L, "음료");

        Categories categories = new Categories();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);

        when(categoryService.getAll()).thenReturn(categories);

        doNothing().when(jwtTokenService).verifyAccessToken(any());

        this.mockMvc.perform(get("/api/v1/categories")
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("[].id").description("카테고리 Id"),
                                        fieldWithPath("[].name").description("카테고리 이름")
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
        doNothing().when(jwtTokenService).verifyAccessToken(any());

        this.mockMvc.perform(get("/api/v1/category/{id}/ingredients", 1L)
                        .locale(locales)
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

    @Test
    @DisplayName("Delete 200")
    public void delete_200() throws Exception {
        doNothing().when(categoryService).delete(1L);
        this.mockMvc.perform(delete("/api/v1/category/{id}", 1L)
                        .header("Authorization", "Bearer AccessToken"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("id").description("카테고리 Id")
                        ),
                        responseFields(
                                fieldWithPath("size").description("Delete 된 갯수")
                        )
                ));
    }

    @Test
    @DisplayName("getAllWithIngredients 200")
    public void getAllWithIngredients_200() throws Exception {

        Category category1 = new Category(1L, "고기", "Meat");
        new Ingredient(1L, "안심", "https://testUrl/Img/1.jpg", category1);
        new Ingredient(2L, "등심", "https://testUrl/Img/2.jpg", category1);

        Category category2 = new Category(2L, "채소", "Vegetable");
        new Ingredient(3L, "대파", "https://testUrl/Img/3.jpg", category2);
        new Ingredient(4L, "마늘", "https://testUrl/Img/4.jpg", category2);
        new Ingredient(5L, "쌍추", "https://testUrl/Img/5.jpg", category2);

        Category category3 = new Category(3L, "과일", "Fruit");
        new Ingredient(6L, "레몬", "https://testUrl/Img/6.jpg", category3);

        Categories categories = new Categories();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        when(categoryService.getAllWithIngredients()).thenReturn(categories);
//        doNothing().when(jwtTokenService.verifyAccessToken(any()));

        this.mockMvc.perform(get("/api/v1/category/all/ingredients")
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("[].id").description("카테고리 Id"),
                                        fieldWithPath("[].name").description("카테고리 이름"),
                                        fieldWithPath("[].ingredients[].ingredientId").description("재료 Id"),
                                        fieldWithPath("[].ingredients[].categoryId").description("카테고리 Id"),
                                        fieldWithPath("[].ingredients[].name").description("재료 이름"),
                                        fieldWithPath("[].ingredients[].imageUrl").description("이미지 URL")
                                )
                        ));
    }


}
