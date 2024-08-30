package com.side.cooknow.domain.useritem.controller;

import com.side.cooknow.document.RestDocsTestSupport;
import com.side.cooknow.domain.ingredient.model.*;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.useritem.model.UserItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("UserItem Controller 테스트")
public class UserItemControllerTest extends RestDocsTestSupport {

    private Locale[] locales = {Locale.KOREAN, Locale.US};
    private List<UserItem> userItems;

    @Test
    @DisplayName("저장")
    public void save() throws Exception {

        final Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .korName(new Name("사과"))
                .engName(new Name("apple"))
                .image(new Image("testUrl"))
                .storageType(StorageType.ROOM)
                .countType(CountType.AMOUNT)
                .expirationPeriod(new Day(30))
                .build();

        User user = new User();

        final UserItem userItem = UserItem.builder()
                .id(1L)
                .user(user)
                .ingredient(ingredient)
                .quantity(10)
                .expirationDate(LocalDate.now())
                .storageType(StorageType.COLD)
                .build();

        userItems = new ArrayList<>();
        userItems.add(userItem);
        userItems.add(userItem);
        userItems.add(userItem);

        when(userItemService.save(any(), any())).thenReturn(userItems);

        this.mockMvc.perform(post("/api/v1/user/{userId}/items", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken")
                        .content("{\"itemList\":[{\"ingredientId\":1,\"quantity\":15,\"expirationDate\":\"2023-10-10\",\"storageType\":\"COLD\"},{\"ingredientId\":2,\"quantity\":30,\"expirationDate\":\"2023-10-12\",\"storageType\":\"ROOM\"},{\"ingredientId\":2,\"quantity\":70,\"expirationDate\":\"2024-08-20\",\"storageType\":\"FREEZE\"}]}"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("userId").description("유저 id")
                                ),
                                requestFields(
                                        fieldWithPath("itemList[].ingredientId").description("재료 id"),
                                        fieldWithPath("itemList[].quantity").description("재료 양"),
                                        fieldWithPath("itemList[].expirationDate").description("만료 날짜"),
                                        fieldWithPath("itemList[].storageType").description("보관 방식")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.userId").description("유저 Id"),
                                        fieldWithPath("data.itemList[].id").description("저장된 유저아이템 id"),
                                        fieldWithPath("data.itemList[].ingredientId").description("유저아이템 재료 id"),
                                        fieldWithPath("data.itemList[].quantity").description("저장된 유저아이템 갯수"),
                                        fieldWithPath("data.itemList[].expirationDate").description("저장된 유저아이템 만료날짜"),
                                        fieldWithPath("data.itemList[].storageType").description("저장된 유저아이템 보관 방식")
                                )
                        )
                );
    }

    @Test
    @DisplayName("삭제")
    public void delete_200() throws Exception {
        doNothing().when(jwtTokenService).verifyAccessToken(any());
        when(userItemService.delete(any())).thenReturn(Arrays.asList(1L, 2L, 3L));

        this.mockMvc.perform(delete("/api/v1/user/{userId}/items", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken")
                        .content("{\"ids\":[1,2,3]}"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("userId").description("유저 id")
                                ),
                                requestFields(
                                        fieldWithPath("ids").description("삭제 할 유저아이템 ids")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.ids").description("삭제 된 유저아이템 ids")
                                )
                        )
                );
    }

    @Test
    @DisplayName("수정")
    public void update_200() throws Exception {

        final Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .korName(new Name("사과"))
                .engName(new Name("apple"))
                .image(new Image("testUrl"))
                .storageType(StorageType.ROOM)
                .countType(CountType.AMOUNT)
                .expirationPeriod(new Day(30))
                .build();

        User user = new User();

        final UserItem userItem = UserItem.builder()
                .id(1L)
                .user(user)
                .ingredient(ingredient)
                .quantity(10)
                .expirationDate(LocalDate.now())
                .storageType(StorageType.COLD)
                .build();

        userItems = new ArrayList<>();
        userItems.add(userItem);
        userItems.add(userItem);
        userItems.add(userItem);

        when(userItemService.update(any(), any())).thenReturn(userItems);

        this.mockMvc.perform(put("/api/v1/user/{userId}/items", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken")
                        .content("{\"itemList\":[{\"id\":1,\"quantity\":20,\"expirationDate\":\"2025-02-28\",\"storageType\":\"FREEZE\"},{\"id\":3,\"quantity\":20,\"expirationDate\":\"2025-12-31\",\"storageType\":\"COLD\"}]}"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("userId").description("유저 id")
                                ),
                                requestFields(
                                        fieldWithPath("itemList[].id").description("유저아이템 id"),
                                        fieldWithPath("itemList[].quantity").description("저장할 재료 양"),
                                        fieldWithPath("itemList[].expirationDate").description("유통 기한"),
                                        fieldWithPath("itemList[].storageType").description("보관 방식")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.userId").description("유저 Id"),
                                        fieldWithPath("data.itemList[].id").description("저장된 유저아이템 id"),
                                        fieldWithPath("data.itemList[].ingredientId").description("유저아이템 재료 id"),
                                        fieldWithPath("data.itemList[].quantity").description("저장된 유저아이템 갯수"),
                                        fieldWithPath("data.itemList[].expirationDate").description("저장된 유저아이템 만료날짜"),
                                        fieldWithPath("data.itemList[].storageType").description("저장된 유저아이템 보관 방식")
                                )
                        )
                );
    }

    @Test
    @DisplayName("조회")
    public void find() throws Exception {
        User user = User.builder()
                .id(1L)
                .build();

        Ingredient ingredient = Ingredient.builder()
                .id(1L)
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

        UserItem userItem2 = UserItem.builder()
                .user(user)
                .storageType(StorageType.ROOM)
                .quantity(15)
                .ingredient(ingredient)
                .expirationDate(LocalDate.now())
                .build();

        UserItem userItem3 = UserItem.builder()
                .user(user)
                .storageType(StorageType.FREEZE)
                .quantity(30)
                .ingredient(ingredient)
                .expirationDate(LocalDate.now())
                .build();


        userItems = new ArrayList<>();
        userItems.add(userItem);
        userItems.add(userItem2);
        userItems.add(userItem3);

        when(userItemService.find(any())).thenReturn(userItems);


        this.mockMvc.perform(get("/api/v1/user/{userId}/items", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken")
                        .locale(locales)
                )
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("userId").description("유저 id")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.userId").description("유저 id"),
                                        fieldWithPath("data.itemList[].id").description("유저아이템 id"),
                                        fieldWithPath("data.itemList[].ingredientId").description("재료 id"),
                                        fieldWithPath("data.itemList[].name").description("유저아이템 이름"),
                                        fieldWithPath("data.itemList[].quantity").description("재료 양"),
                                        fieldWithPath("data.itemList[].expirationDate").description("만료 날짜"),
                                        fieldWithPath("data.itemList[].storageType").description("저장된 장소"),
                                        fieldWithPath("data.itemList[].url").description("재료 이미지 url")
                                )
                        )
                );
    }

    @Test
    @DisplayName("만료 임박 조회")
    public void near_expiry() throws Exception {
        User user = User.builder()
                .id(1L)
                .build();

        Ingredient ingredient = Ingredient.builder()
                .id(1L)
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

        when(userItemService.getNearExpiry(any())).thenReturn(userItems);

        this.mockMvc.perform(get("/api/v1/user/{userId}/items/near-expiry", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer AccessToken"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("userId").description("유저 id")
                                ),
                                relaxedResponseFields(
                                        fieldWithPath("data.userId").description("유저스토리지 id"),
                                        fieldWithPath("data.itemList[].id").description("유저아이템 id"),
                                        fieldWithPath("data.itemList[].ingredientId").description("재료 id"),
                                        fieldWithPath("data.itemList[].name").description("유저아이템 이름"),
                                        fieldWithPath("data.itemList[].quantity").description("재료 양"),
                                        fieldWithPath("data.itemList[].expirationDate").description("만료 날짜"),
                                        fieldWithPath("data.itemList[].storageType").description("보관 방식"),
                                        fieldWithPath("data.itemList[].url").description("재료 이미지 url")
                                )
                        )
                );
    }
}
