package com.side.cooktime.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.cooktime.global.config.auth.LoginResponse;
import com.side.cooktime.global.config.auth.jwt.TokenProvider;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.model.User;
import com.side.cooktime.domain.member.model.dto.GoogleUserDto;
import com.side.cooktime.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class GoogleService {

    private final String GRANT_TYPE = "authorization_code";
    private final String ACCESS_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID;

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public Member login(String authorizationCode, String redirectUri) throws JsonProcessingException {
        log.info("## [REQUEST] authorizationCode : {} / redirectUri : {}", authorizationCode, redirectUri);

        Member loginUser = null;

        String accessToken = getGoogleAccessToken(authorizationCode, redirectUri);
        GoogleUserDto googleUserDto = getGoogleUser(accessToken);

        Optional<Member> optionalMember = memberRepository.findByEmail(googleUserDto.getEmail());

        if (optionalMember.isEmpty()) {
            log.info("회원가입 진행");
            User newUser = googleUserDto.toUserEntity();
            loginUser = memberRepository.save(newUser);
        } else {
            loginUser = optionalMember.get();
        }

        return loginUser;
    }

    /**
     * Access Token 획득
     *
     * @param authorizationCode
     * @return Access Token
     */
    private String getGoogleAccessToken(String authorizationCode, String redirectUri) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", authorizationCode);
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("grant_type", GRANT_TYPE);

        HttpEntity<MultiValueMap<String, String>> restRequest = new HttpEntity<>(requestBody, headers);

        URI uri = URI.create(ACCESS_TOKEN_URL);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity;

        try {
            responseEntity = restTemplate.postForEntity(uri, restRequest, String.class);
        } catch (HttpClientErrorException e) {
            //TODO: 예외처리
            log.error("API 요청 실패 / URI : {} / StatusCode : {} / ResponseBody : {}", ACCESS_TOKEN_URL, e.getStatusCode(), e.getResponseBodyAsString());
            return e.getResponseBodyAsString();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        String accessToken = jsonNode.get("access_token").asText();
        log.info("Access Token : {}", accessToken);
        return accessToken;
    }

    /**
     * Google User Info 획득
     *
     * @param accessToken
     * @return GoogleUserDto
     */
    private GoogleUserDto getGoogleUser(String accessToken) {
        URI uri = URI.create(USER_INFO_URL + "?access_token=" + accessToken);
        RestTemplate restTemplate = new RestTemplate();
        GoogleUserDto googleUserDto = null;
        try {
            googleUserDto = restTemplate.getForObject(uri, GoogleUserDto.class);
        } catch (HttpClientErrorException e) {
            //TODO: 예외처리
            log.error("API 요청 실패 / URI : {} / StatusCode : {} / ResponseBody : {}", USER_INFO_URL, e.getStatusCode(), e.getResponseBodyAsString());
        }
        log.info("GoogleUserInfo : {}", googleUserDto);
        return googleUserDto;
    }

    public LoginResponse getJwtResponse(Member loginMember) {
        log.info("generate JWT");
        String jwtToken = tokenProvider.generateJwtToken(loginMember.getEmailValue(), loginMember.getRoleKey());
        log.info("JWT Token: {}", jwtToken);
        return new LoginResponse(jwtToken, loginMember.getFullName(), false, false, false);
    }

    //TODO: postman 테스트용 메서드이므로 삭제 필요
    public Member loginForTest(String email) {
        Optional<Member> loginUser = memberRepository.findByEmail(email);
        return loginUser.get();
    }
}
