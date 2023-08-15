package com.side.cooktime.domain.userstorage.service;

import com.side.cooktime.config.auth.OAuth2UserUtils;
import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.service.IngredientService;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.service.MemberService;
import com.side.cooktime.domain.model.BaseEntity;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import com.side.cooktime.domain.userstorage.model.UserStorages;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestUpdateDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestUpdateOneDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseGetDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseSaveDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseUpdateDto;
import com.side.cooktime.domain.userstorage.repository.UserStorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class UserStorageService {

    private final UserStorageRepository userStorageRepository;
    private final MemberService memberService;
    private final IngredientService ingredientService;

    private Member getCurrentMember() {
        return memberService.findByEmail(OAuth2UserUtils.getEmail(SecurityContextHolder.getContext().getAuthentication()));
    }

    public List<UserStorage> save(RequestSaveDto requestDto) {
        Member member = getCurrentMember();
        List<Long> ingredientIds = requestDto.getIngredientIds();
        List<Ingredient> ingredients = ingredientIds.stream()
                .map(ingredientService::getReferenceById) /*TODO:예외처리*/
                .toList();
        return requestDto.toEntities(member, ingredients);
    }

    public UserStorages update(RequestUpdateDto requestDto) {
        Member member = getCurrentMember();
        List<UserStorage> updatedUserStorages = new ArrayList<>();
        for (RequestUpdateOneDto requestOne : requestDto.getRequest()) {
            UserStorage userStorage = userStorageRepository.findByIdAndMember(requestOne.getId(), member);
            updatedUserStorages.add(userStorage.update(requestOne));
        }
        return new UserStorages(updatedUserStorages);
    }

    public ResponseDeleteDto delete(RequestDeleteDto requestDto) {
        Member member = getCurrentMember();
        List<UserStorage> userStorages = userStorageRepository.findByIdInAndMember(requestDto.getIds(), member);
        userStorages.forEach(BaseEntity::delete);
        return new ResponseDeleteDto(userStorages.size());
    }

    public UserStorages get() {
        Member member = getCurrentMember();
        List<UserStorage> userStorages = userStorageRepository.findAllByMemberAndDeletedAtIsNull(member);
        return new UserStorages(userStorages);
    }

    public UserStorages get(Pageable pageable) {
        Member member = getCurrentMember();
        List<UserStorage> userStorages = userStorageRepository.findByMemberAndDeletedAtIsNullOrderByIdDesc(member, pageable).getContent();
        return new UserStorages(userStorages);
    }
}
