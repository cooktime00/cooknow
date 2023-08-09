package com.side.cooktime.domain.userstorage.controller;

import com.side.cooktime.document.RestDocsTestSupport;
import com.side.cooktime.domain.category.model.Category;
import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.model.User;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestSaveOneDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseDeleteDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserStorageControllerTest extends RestDocsTestSupport {

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
                        .content("[{\"id\": 1},{\"id\": 2}]"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("[].id").description("삭제할 유저스토리지 id")
                                )
                        )
                );
    }
}
