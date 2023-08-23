package com.side.cooktime.global.config.auth;

import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.model.User;
import com.side.cooktime.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class CustomOAuth2AuthorizedClientService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    private final HttpSession httpSession;

    public CustomOAuth2AuthorizedClientService(MemberRepository memberRepository, HttpSession httpSession) {
        this.memberRepository = memberRepository;
        this.httpSession = httpSession;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser");

        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        String email = "";
        String given_name = "";
        String family_name = "";
        Map<String, Object> response = oAuth2User.getAttributes();

        if (registrationId.equals("naver")) {
            Map<String, Object> hash = (Map<String, Object>) response.get("response");
            email = (String) hash.get("email");
        } else if (registrationId.equals("google")) {
            email = (String) response.get("email");
            given_name = (String) response.get("given_name");
            family_name = (String) response.get("family_name");
        } else {
            throw new OAuth2AuthenticationException("허용되지 않은 인증입니다.");
        }

        SessionUser sessionUser;
        User user;
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if (optionalMember.isPresent()) {
            log.info("이미 가입되어 있는 회원, 로그인 진행");
            user = (User) optionalMember.get();
            sessionUser = new SessionUser(user);
        } else {
            log.info("회원가입 및 로그인 진행");
            String provider = userRequest.getClientRegistration().getRegistrationId(); // google
            String providerId = oAuth2User.getAttribute(userNameAttributeName);

            ResponseEntity<Map> additionalResponse = new RestTemplate()
                    .exchange("https://people.googleapis.com/v1/people/" + response.get(userNameAttributeName) + "?personFields=genders,birthdays",
                            HttpMethod.GET,
                            new HttpEntity<>(createHeaders(userRequest.getAccessToken().getTokenValue())),
                            Map.class);

            String gender = null;
            List<Map<String, Object>> gendersList = (List<Map<String, Object>>) additionalResponse.getBody().get("genders");
            if (gendersList != null && !gendersList.isEmpty()) {
                gender = (String) gendersList.get(0).get("value");
            }

            String birthYear = null;
            List<Map<String, Object>> birthdaysList = (List<Map<String, Object>>) additionalResponse.getBody().get("birthdays");
            if (birthdaysList != null && !birthdaysList.isEmpty()) {
                Map<String, Object> dateMap = (Map<String, Object>) birthdaysList.get(0).get("date");
                if (dateMap != null) {
                    birthYear = String.valueOf(dateMap.get("year"));
                }
            }
            int currentYear = LocalDate.now().getYear();
            int age = currentYear - Integer.parseInt(birthYear);

            user = new User(provider, providerId, email, "pass_word", given_name, family_name, gender, age);
            memberRepository.save(user);
            sessionUser = new SessionUser(email, given_name, family_name);
        }

        httpSession.setAttribute("user", sessionUser);


        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        String accessTokenValue = accessToken.getTokenValue();
        log.info("accessTokenValue : {}",accessTokenValue);
        log.info("role : {}",user.getRoleKey());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                oAuth2User.getAttributes(),
                userNameAttributeName
        );
    }

    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return headers;
    }
}
