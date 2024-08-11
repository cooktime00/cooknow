package com.side.cooknow.global.controller;

import com.side.cooknow.document.RestDocsTestSupport;
import com.side.cooknow.domain.user.model.Role;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.model.security.RefreshToken;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OAuthControllerTest extends RestDocsTestSupport {

    private final static String ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJDb29rTm93Iiwic3ViIjoiQWNjZXNzIFRva2VuIiwiYXVkIjpbInRlc3RAY29va25vdy5jb20iXSwiaWF0IjoxNzE5Njc1MTkwLCJleHAiOjE3MTk2Nzg3OTAsImp0aSI6IjU5YzQzMTI1LTI2YTUtNGMwYi04NzBjLTYwNGY1YzM0NDU1YSIsInVzZXJJZCI6MSwicm9sZSI6IlVTRVIifQ.Rr0nOa5k8IuXrJV_IiOzMJiy18GT82Nk4yFbBmb3ZXc";

    @Test
    @DisplayName("Sign-In 200")
    public void signIn_200() throws Exception {

        final User user = new User(1L, "email", "test@cooknow.com", "Cook", "Now", Role.USER);

        final RefreshToken refreshToken = new RefreshToken(user);

        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);
        when(userService.existsByEmail(any())).thenReturn(true);
        when(firebaseService.verifyToken(any())).thenReturn("test@cooknow.com");
        when(jwtTokenService.createAccessToken(any())).thenReturn(ACCESS_TOKEN);
        when(refreshTokenService.createToken(any())).thenReturn(refreshToken);


        this.mockMvc.perform(post("/oauth/sign-in")
                        .header("Authorization", "Bearer FirebaseToken"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("accessToken").description("Access Token"),
                                        fieldWithPath("refreshToken").description("Refresh Token")
                                )
                        ))
                .andReturn();
    }

    @Test
    @DisplayName("Refresh 200")
    public void refresh_200() throws Exception {
        final User user = new User(1L, "email", "test@cooknow.com", "Cook", "Now", Role.USER);

        final RefreshToken refreshToken = new RefreshToken(user, 1209600000);

        when(refreshTokenService.findByToken(any())).thenReturn(refreshToken);
        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);
        when(jwtTokenService.refreshAccessToken(user)).thenReturn(ACCESS_TOKEN);

        this.mockMvc.perform(post("/oauth/refresh")
                        .header("Authorization", "Bearer RefreshToken"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("accessToken").description("Access Token")
                                )
                        ));
    }

    @Test
    @DisplayName("Sign-Out 200")
    public void signout_200() throws Exception {
        final User user = new User(1L, "email", "test@cooknow.com", "Cook", "Now", Role.USER);

        final RefreshToken refreshToken = new RefreshToken(user);

        doNothing().when(firebaseService).deleteUser();
        doNothing().when(refreshTokenService).deleteAllByUser(user);

        this.mockMvc.perform(post("/oauth/sign-out")
                        .header("Authorization", "Bearer AccessToken"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("result").description("Successfully signed out")
                                )
                        ));
    }

    @Test
    @DisplayName("Withdraw 200")
    public void withdraw_200() throws Exception {
        final User user = new User(1L, "email", "test@cooknow.com", "Cook", "Now", Role.USER);

        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);

        this.mockMvc.perform(post("/oauth/sign-out")
                        .header("Authorization", "Bearer AccessToken"))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("result").description("SuccessFully withdrawn")
                                )
                        ));
    }
}
