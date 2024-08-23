package com.side.cooknow.domain.ingredient.controller;

import com.side.cooknow.document.RestDocsTestSupport;
import com.side.cooknow.domain.ingredient.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Ingredient Controller 테스트")
public class IngredientControllerTest extends RestDocsTestSupport {

    private Locale[] locales = {Locale.KOREA, Locale.US};

//    @Test
//    @DisplayName("저장")
//    public void save() throws Exception {
//        final Ingredient ingredient = new Ingredient("등심", "testUrl", 30, StorageType.FREEZE, "고기", CountType.AMOUNT);
//
//        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());
//
//
//        when(ingredientService.save(any())).thenReturn(ingredient);
//        doNothing().when(jwtTokenService).verifyAccessToken(any());
//
//        this.mockMvc.perform(post("/api/v1/ingredient")
//                        .header("Authorization", "Bearer AccessToken")
//                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                        .content("{\"name\": \"채소\"}"))
//                .andExpect(status().isOk())
//                .andDo(
//                        restDocs.document(
//                                requestFields(
//                                        fieldWithPath("categoryId").description("대분류 id"),
//                                        fieldWithPath("korName").description("재료 대분류 이름"),
//                                        fieldWithPath("engName").description("재료 대분류 이름"),
//                                        fieldWithPath("expirationPeriod").description("재료 대분류 이름"),
//                                        fieldWithPath("storage").description("재료 대분류 이름"),
//                                        fieldWithPath("countType").description("재료 대분류 이름")
//                                ),
//                                relaxedResponseFields(
//                                        fieldWithPath("id").description("ID"),
//                                        fieldWithPath("category").description("대분류 이름"),
//                                        fieldWithPath("name").description("재료 이름")
//                                )
//                        ));
//    }

    @Test
    @DisplayName("조회")
    public void findIngredient() throws Exception {
        final Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .korName(new Name("사과"))
                .engName(new Name("apple"))
                .image(new Image("testUrl"))
                .storageType(StorageType.ROOM)
                .countType(CountType.AMOUNT)
                .expirationPeriod(new Day(30))
                .build();

        when(ingredientService.findById(1L)).thenReturn(ingredient);

        this.mockMvc.perform(get("/api/v1/ingredient/{id}", 1L)
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("재료 id")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.id").description("재료 id"),
                                        fieldWithPath("data.name").description("재료 이름"),
                                        fieldWithPath("data.imageUrl").description("재료 이미지"),
                                        fieldWithPath("data.expirationDate").description("재료 마감 날짜"),
                                        fieldWithPath("data.type").description("재료 저장 타입"),
                                        fieldWithPath("data.count").description("재료 갯수")
                                )
                        ));
    }

    @Test
    @DisplayName("조회 ALL")
    public void findIngredients() throws Exception {
//        List<Long> ids = List.of(1L, 2L, 3L);

        final Ingredient ingredient1 = Ingredient.builder()
                .id(1L)
                .korName(new Name("사과"))
                .engName(new Name("apple"))
                .image(new Image("testUrl"))
                .storageType(StorageType.ROOM)
                .countType(CountType.AMOUNT)
                .expirationPeriod(new Day(30))
                .build();

        final Ingredient ingredient2 = Ingredient.builder()
                .id(2L)
                .korName(new Name("사과"))
                .engName(new Name("apple"))
                .image(new Image("testUrl"))
                .storageType(StorageType.ROOM)
                .countType(CountType.AMOUNT)
                .expirationPeriod(new Day(30))
                .build();

        final Ingredient ingredient3 = Ingredient.builder()
                .id(3L)
                .korName(new Name("사과"))
                .engName(new Name("apple"))
                .image(new Image("testUrl"))
                .storageType(StorageType.ROOM)
                .countType(CountType.AMOUNT)
                .expirationPeriod(new Day(30))
                .build();

        Ingredients ingredients = new Ingredients(List.of(ingredient1, ingredient2, ingredient3));

        when(ingredientService.findByIds(any())).thenReturn(ingredients);

        this.mockMvc.perform(get("/api/v1/ingredients")
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                        .param("ids", "1", "2", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                queryParameters(
                                        parameterWithName("ids").description("재료 ids")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.[].id").description("재료 id"),
                                        fieldWithPath("data.[].name").description("재료 이름"),
                                        fieldWithPath("data.[].imageUrl").description("재료 이미지"),
                                        fieldWithPath("data.[].expirationDate").description("재료 마감 날짜"),
                                        fieldWithPath("data.[].type").description("재료 저장 타입"),
                                        fieldWithPath("data.[].count").description("재료 갯수")
                                )
                        ));
    }

//    @Test
//    @DisplayName("삭제")
//    public void delete_200() throws Exception {
//        doNothing().when(ingredientService).delete(1L);
//        this.mockMvc.perform(delete("/api/v1/ingredient/{id}", 1L)
//                        .header("Authorization", "Bearer AccessToken"))
//                .andExpect(status().isOk())
//                .andDo(restDocs.document(
//                        pathParameters(
//                                parameterWithName("id").description("카테고리 Id")
//                        ),
//                        relaxedResponseFields(
//                                fieldWithPath("id").description("삭제된 Id")
//                        )
//                ));
//    }
}
