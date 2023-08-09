package com.side.cooktime.domain.userstorage.controller;

import com.side.cooktime.document.RestDocsTestSupport;
import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.model.User;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestSaveOneDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseSaveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserStorageControllerTest extends RestDocsTestSupport {

    //TODO: Authentication이 null이라서 현재 테스트 불가
//    @Test
//    @DisplayName("Create 201")
//    public void create_201() throws Exception {
//        Member member = new User("google", "test", "test@gmail.com", "test123456", "길동", "홍", "MALE", 29);
//        Long categoryId = 1L;
//        Category category = new Category(categoryId, "고기");
//        Ingredient ingredient1 = new Ingredient(1L, "안심", "testUrl", category);
//        Ingredient ingredient2 = new Ingredient(2L, "등심", "testUrl", category);
//
//        RequestSaveOneDto requestSaveOneDto = new RequestSaveOneDto();
//        requestSaveOneDto.setIngredient_id(1L);
//        requestSaveOneDto.setQuantity(15);
//        requestSaveOneDto.setExpiration_date(LocalDate.parse("2023-08-10", DateTimeFormatter.ISO_DATE));
//        requestSaveOneDto.setStorage_type("COLD");
//        UserStorage userStorage1 = requestSaveOneDto.toEntity(member, ingredient1);
//
//        RequestSaveOneDto requestSaveOneDto2 = new RequestSaveOneDto();
//        requestSaveOneDto2.setIngredient_id(2L);
//        requestSaveOneDto2.setQuantity(30);
//        requestSaveOneDto2.setExpiration_date(LocalDate.parse("2023-08-12", DateTimeFormatter.ISO_DATE));
//        requestSaveOneDto2.setStorage_type("ROOM");
//        UserStorage userStorage2 = requestSaveOneDto2.toEntity(member, ingredient2);
//
//        RequestSaveOneDto requestSaveOneDto3 = new RequestSaveOneDto();
//        requestSaveOneDto3.setIngredient_id(2L);
//        requestSaveOneDto3.setQuantity(70);
//        requestSaveOneDto3.setExpiration_date(LocalDate.parse("2023-09-30", DateTimeFormatter.ISO_DATE));
//        requestSaveOneDto3.setStorage_type("FREEZE");
//        UserStorage userStorage3 = requestSaveOneDto3.toEntity(member, ingredient2);
//
//        List<UserStorage> userStorages = new ArrayList<>();
//        userStorages.add(userStorage1);
//        userStorages.add(userStorage2);
//        userStorages.add(userStorage3);
//
//        ResponseSaveDto responseSaveDto = new ResponseSaveDto("test@gmail.com",userStorages);
//
//        when(userStorageService.save(any(), any())).thenReturn(responseSaveDto);
//
//        this.mockMvc.perform(post("/api/v1/storage")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"request\": [{\"ingredient_id\": 1,\"quantity\": 15,\"expiration_date\": \"2023-08-10\", \"storage_type\": \"COLD\"},{\"ingredient_id\": 2,\"quantity\": 30,\"expiration_date\": \"2023-08-12\",\"storage_type\": \"ROOM\"},{\"ingredient_id\": 2,\"quantity\": 70,\"expiration_date\": \"2023-09-30\",\"storage_type\": \"FREEZE\"}]}"))
//                .andExpect(status().isCreated())
//                .andDo(
//                        restDocs.document(
//                                requestFields(
//                                        fieldWithPath("request[].ingredient_id").description("저장할 재료 id"),
//                                        fieldWithPath("request[].quantity").description("저장할 재료 양"),
//                                        fieldWithPath("request[].expiration_date").description("유통 기한"),
//                                        fieldWithPath("request[].storage_type").description("보관 방식")
//                                ),
//                                responseFields(
//                                        fieldWithPath("memberEmail").description("저장 요청한 유저 email"),
//                                        fieldWithPath("response[].id").description("저장한 유저스토리지 id"),
//                                        fieldWithPath("response[].ingredientName").description("저장한 재료 이름"),
//                                        fieldWithPath("response[].quantity").description("저장한 재료 양")
//                                )
//                        )
//                );
//    }

    @Test
    @DisplayName("Delete 200")
    public void delete_200() throws Exception {
        Member member = new User("google", "test", "test@gmail.com", "test123456", "길동", "홍", "MALE", 29);
        Long categoryId = 1L;
        Category category = new Category(categoryId, "고기");
        Ingredient ingredient1 = new Ingredient(1L, "안심", "testUrl", category);
        Ingredient ingredient2 = new Ingredient(2L, "등심", "testUrl", category);

        RequestSaveOneDto requestSaveOneDto = new RequestSaveOneDto();
        requestSaveOneDto.setStorage_type("COLD");
        requestSaveOneDto.setQuantity(30);
        requestSaveOneDto.setIngredient_id(1L);
        requestSaveOneDto.setExpiration_date(LocalDate.now());
        UserStorage userStorage1 = requestSaveOneDto.toEntity(member, ingredient1);

        RequestSaveOneDto requestSaveOneDto2 = new RequestSaveOneDto();
        requestSaveOneDto2.setStorage_type("ROOM");
        requestSaveOneDto2.setQuantity(15);
        requestSaveOneDto2.setIngredient_id(2L);
        requestSaveOneDto2.setExpiration_date(LocalDate.now());
        UserStorage userStorage2 = requestSaveOneDto2.toEntity(member, ingredient2);

        List<UserStorage> userStorages = new ArrayList<>();
        userStorages.add(userStorage1);
        userStorages.add(userStorage2);
        ResponseDeleteDto responseDeleteDto = new ResponseDeleteDto("test@gmail.com", userStorages);

        when(userStorageService.delete(any(), any())).thenReturn(responseDeleteDto);

        this.mockMvc.perform(delete("/api/v1/storage/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"request\": [{\"id\": 1},{\"id\": 2}]}"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("request[].id").description("삭제할 유저스토리지 id")
                                ),
                                responseFields(
                                        fieldWithPath("memberEmail").description("삭제 요청한 유저 email"),
                                        fieldWithPath("response[].id").description("삭제한 유저스토리지 id"),
                                        fieldWithPath("response[].ingredient_name").description("재료 이름")
                                )
                        )
                );
    }
}
