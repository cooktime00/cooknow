package com.side.cooktime.domain.userstorage.service;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.service.IngredientService;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.service.MemberService;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseSaveDto;
import com.side.cooktime.domain.userstorage.repository.UserStorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class UserStorageService {

    private final UserStorageRepository userStorageRepository;
    private final MemberService memberService;
    private final IngredientService ingredientService;

    public ResponseSaveDto saveAll(String memberEmail, RequestSaveDto requestDto) {
        Member member = memberService.findByEmail(memberEmail);
        if (member == null) { /*TODO:예외처리*/ }

        List<Long> ingredientIds = requestDto.getIngredientIds();
        List<Ingredient> ingredients = ingredientIds.stream()
                .map(ingredientService::getReferenceById) /*TODO:예외처리*/
                .toList();
        List<UserStorage> userStorages = requestDto.toEntities(member, ingredients);

        return new ResponseSaveDto(memberEmail, userStorageRepository.saveAll(userStorages));
    }

    public ResponseDeleteDto delete(String memberEmail, List<RequestDeleteDto> requestDtos) {
        Member member = memberService.findByEmail(memberEmail);
        List<Long> deleteRequestedIds = requestDtos.stream().map(RequestDeleteDto::getId).toList();
        List<UserStorage> userStorages = userStorageRepository.findByIdInAndMember(deleteRequestedIds, member);
        for (UserStorage userStorage : userStorages) {
            userStorage.delete();
        }
        return new ResponseDeleteDto(memberEmail, userStorages);
    }
}
