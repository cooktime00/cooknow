package com.side.cooknow.domain.category.controller;

import com.side.cooknow.document.RestDocsTestSupport;
import com.side.cooknow.domain.category.model.Categories;
import com.side.cooknow.domain.category.model.Category;
import com.side.cooknow.domain.ingredient.model.Ingredients;
import com.side.cooknow.domain.ingredient.model.Ingredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
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

@DisplayName("Category Controller 테스트")
class CategoryControllerTest extends RestDocsTestSupport {

    private Locale[] locales = {Locale.KOREA, Locale.US};

    @Test
    @DisplayName("저장")
    public void save() throws Exception {
        final Category category = new Category(1L, "채소");
        when(categoryService.save(any())).thenReturn(category);

        this.mockMvc.perform(post("/api/v1/category")
                        .header("Authorization", "Bearer AccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"korName\":\"과일\",\"engName\":\"Fruit\"}"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("korName").description("대분류 한글 이름"),
                                        fieldWithPath("engName").description("대분류 영어 이름")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.id").description("대분류 id"),
                                        fieldWithPath("data.korName").description("대분류 한글 이름"),
                                        fieldWithPath("data.engName").description("대분류 영어 이름")
                                )
                        ));
    }

    @Test
    @DisplayName("조회 All")
    public void findAll() throws Exception {

        Category category1 = new Category(1L, "고기");
        Category category2 = new Category(2L, "채소");
        Category category3 = new Category(3L, "해산물");
        Category category4 = new Category(4L, "음료");

        Categories categories = new Categories();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);

        when(categoryService.findAll()).thenReturn(categories);

        this.mockMvc.perform(get("/api/v1/categories")
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        fieldWithPath("data.[].id").description("대분류 Id"),
                                        fieldWithPath("data.[].name").description("대분류 이름")
                                )
                        ));
    }

    @Test
    @DisplayName("조회 with 재료")
    public void findIngredients() throws Exception {
        Long categoryId = 1L;
        Category category = new Category(categoryId, "고기");

        Ingredient ingredient1 = new Ingredient(1L, "안심", "testUrl", category);
        Ingredient ingredient2 = new Ingredient(2L, "등심", "testUrl", category);
        Ingredients ingredients = new Ingredients(Arrays.asList(ingredient1, ingredient2));

        when(categoryService.findOneIngredients(1L)).thenReturn(category);

        this.mockMvc.perform(get("/api/v1/category/{id}/ingredients", 1L)
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("카테고리 Id")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.id").description("대분류 Id"),
                                        fieldWithPath("data.name").description("대분류 이름"),
                                        fieldWithPath("data.ingredientList[].id").description("재료 Id"),
                                        fieldWithPath("data.ingredientList[].name").description("재료 이름"),
                                        fieldWithPath("data.ingredientList[].imageUrl").description("재료 이미지")
                                )
                        ));
    }

    @Test
    @DisplayName("조회 All with 재료")
    public void findAllWithIngredients() throws Exception {

        Category category1 = new Category(1L, "고기", "Meat");
        new Ingredient(1L, "안심", "https://testUrl/Img/1.jpg", category1);
        new Ingredient(2L, "등심", "https://testUrl/Img/2.jpg", category1);

        Category category2 = new Category(2L, "채소", "Vegetable");
        new Ingredient(3L, "대파", "https://testUrl/Img/3.jpg", category2);
        new Ingredient(4L, "마늘", "https://testUrl/Img/4.jpg", category2);
        new Ingredient(5L, "쌍추", "https://testUrl/Img/5.jpg", category2);

        Category category3 = new Category(3L, "과일", "Fruit");
        new Ingredient(6L, "레몬", "https://testUrl/Img/6.jpg", category3);

        List<Category> categories = Arrays.asList(category1, category2, category3);
        when(categoryService.findAllWithIngredients()).thenReturn(categories);

        this.mockMvc.perform(get("/api/v1/categories/ingredients")
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                relaxedResponseFields(
                                        fieldWithPath("data.categoryList[].id").description("대분류 Id"),
                                        fieldWithPath("data.categoryList[].name").description("대분류 이름"),
                                        fieldWithPath("data.categoryList[].ingredientList[].id").description("재료 Id"),
                                        fieldWithPath("data.categoryList[].ingredientList[].name").description("재료 이름"),
                                        fieldWithPath("data.categoryList[].ingredientList[].imageUrl").description("재료 이미지")
                                )
                        ));
    }

    @Test
    @DisplayName("삭제")
    public void categoryDelete() throws Exception {
        doNothing().when(categoryService).delete(1L);
        this.mockMvc.perform(delete("/api/v1/category/{id}", 1L)
                        .header("Authorization", "Bearer AccessToken"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("id").description("카테고리 Id")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("data.size").description("Delete 된 갯수")
                        )
                ));
    }
}
