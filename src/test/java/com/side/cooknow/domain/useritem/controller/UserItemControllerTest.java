package com.side.cooknow.domain.useritem.controller;

import com.side.cooknow.document.RestDocsTestSupport;
import com.side.cooknow.domain.ingredient.model.Image;
import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.Name;
import com.side.cooknow.domain.ingredient.model.StorageType;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.useritem.model.UserItem;
import com.side.cooknow.domain.useritem.model.UserItems;
import com.side.cooknow.domain.useritem.model.dto.response.ResponseDeleteDto;
import com.side.cooknow.domain.useritem.model.dto.response.ResponseFindDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@Sql({"/data.sql"})
public class UserItemControllerTest extends RestDocsTestSupport {

    private Locale[] locales = {Locale.KOREAN, Locale.US};

    private User user;
    private List<UserItem> userItems;
    private List<Ingredient> ingredients;

    @Test
    @DisplayName("Save")
    public void save_201() throws Exception {

        User user = new User();
        userItems = new ArrayList<>();
        userItems.add(new UserItem(user));
        userItems.add(new UserItem(user));
        userItems.add(new UserItem(user));

        doNothing().when(jwtTokenService).verifyAccessToken(any());
        when(userItemService.save(any())).thenReturn(userItems);

        this.mockMvc.perform(post("/api/v1/useritems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken")
                        .content("{\"userId\":1,\"items\":[{\"id\":1,\"quantity\":15,\"expirationDate\":\"2023-10-10\",\"storageType\":\"COLD\"},{\"id\":2,\"quantity\":30,\"expirationDate\":\"2023-10-12\",\"storageType\":\"ROOM\"},{\"id\":2,\"quantity\":70,\"expirationDate\":\"2024-08-20\",\"storageType\":\"FREEZE\"}]}"))
                .andExpect(status().isCreated())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("userId").description("유저 id"),
                                        fieldWithPath("items[].id").description("저장할 재료 id"),
                                        fieldWithPath("items[].quantity").description("저장할 재료 양"),
                                        fieldWithPath("items[].expirationDate").description("유통 기한"),
                                        fieldWithPath("items[].storageType").description("보관 방식")
                                ),
                                responseFields(
                                        fieldWithPath("size").description("Save 된 갯수")
                                )
                        )
                );
    }

    @Test
    @DisplayName("Delete")
    public void delete_200() throws Exception {

        doNothing().when(jwtTokenService).verifyAccessToken(any());
        when(userItemService.delete(any())).thenReturn(new ResponseDeleteDto(3));

        this.mockMvc.perform(delete("/api/v1/useritems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken")
                        .content("{\"id\":[4,5,6]}"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("id").description("삭제할 유아이템 id")
                                ),
                                responseFields(
                                        fieldWithPath("size").description("Delete 된 갯수")
                                )
                        )
                );
    }

    @Test
    @DisplayName("Update 200")
    public void update_200() throws Exception {

        User user = new User();
        userItems = new ArrayList<>();
        userItems.add(new UserItem(user));
        userItems.add(new UserItem(user));
        userItems.add(new UserItem(user));

        doNothing().when(jwtTokenService).verifyAccessToken(any());
        when(userItemService.update(any())).thenReturn(new UserItems(userItems));

        this.mockMvc.perform(put("/api/v1/useritems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken")
                        .content("{\"userId\":1,\"items\":[{\"id\":1,\"quantity\":20,\"expirationDate\":\"2025-02-28\",\"storageType\":\"FREEZE\"},{\"id\":3,\"quantity\":20,\"expirationDate\":\"2025-12-31\",\"storageType\":\"COLD\"}]}"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("userId").description("유저 id"),
                                        fieldWithPath("items[].id").description("저장할 재료 id"),
                                        fieldWithPath("items[].quantity").description("저장할 재료 양"),
                                        fieldWithPath("items[].expirationDate").description("유통 기한"),
                                        fieldWithPath("items[].storageType").description("보관 방식")
                                ),
                                responseFields(
                                        fieldWithPath("size").description("Save 된 갯수")
                                )
                        )
                );
    }

    @Test
    @DisplayName("Find")
    public void find() throws Exception {
        User user = new User();
        Ingredient ingredient = Ingredient.builder()
                .image(new Image("testUrl"))
                .korName(new Name("테스트"))
                .engName(new Name("test"))
                .build();

        UserItem userItem = UserItem.builder()
                .user(user)
                .storageType(StorageType.COLD)
                .quantity(10)
                .ingredient(ingredient)
                .expirationDate(LocalDate.now())
                .build();


        userItems = new ArrayList<>();
        userItems.add(userItem);
        userItems.add(userItem);
        userItems.add(userItem);

        UserItems userItemList = new UserItems(userItems);

        doNothing().when(jwtTokenService).verifyAccessToken(any());
        when(userItemService.find(any(), any(), any())).thenReturn(userItemList.toDtos(ResponseFindDto::new, Locale.KOREA));


        this.mockMvc.perform(get("/api/v1/useritems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                        .content("{\"id\":1,\"type\":\"COLD\"}"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("id").description("유저 id"),
                                        fieldWithPath("type").description("보관 방식")
                                ),
                                responseFields(
                                        fieldWithPath("[].id").description("유저아이템 id"),
                                        fieldWithPath("[].name").description("재료 이름"),
                                        fieldWithPath("[].quantity").description("재료 양"),
                                        fieldWithPath("[].expirationDate").description("유통 기한"),
                                        fieldWithPath("[].storageType").description("보관 방식"),
                                        fieldWithPath("[].url").description("재료 이미지 url")
                                )
                        )
                );
    }

    @Test
    @DisplayName("near-expiry")
    public void near_expiry_200() throws Exception {
        User user = new User();
        Ingredient ingredient = Ingredient.builder()
                .image(new Image("testUrl"))
                .korName(new Name("테스트"))
                .engName(new Name("test"))
                .build();

        UserItem userItem = UserItem.builder()
                .user(user)
                .storageType(StorageType.COLD)
                .quantity(10)
                .ingredient(ingredient)
                .expirationDate(LocalDate.now())
                .build();


        userItems = new ArrayList<>();
        userItems.add(userItem);
        userItems.add(userItem);

        UserItems userItemList = new UserItems(userItems);


        doNothing().when(jwtTokenService).verifyAccessToken(any());
        when(userItemService.getNearExpiry()).thenReturn(userItemList);



        this.mockMvc.perform(get("/api/v1/useritems/near-expiry"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("[].id").description("유저스토리지 id"),
                                        fieldWithPath("[].imageUrl").description("재료 이미지 url")
                                )
                        )
                );
    }
}
