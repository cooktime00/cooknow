package com.side.cooktime.domain.userstorage.service;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.StorageType;
import com.side.cooktime.domain.ingredient.service.IngredientService;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.service.MemberService;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseGetNearExpiryDto;
import com.side.cooktime.global.model.BaseEntity;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import com.side.cooktime.domain.userstorage.model.UserStorages;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestUpdateDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestUpdateOneDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseDeleteDto;
import com.side.cooktime.domain.userstorage.repository.UserStorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private final long WARNING_INDEX_DAY = 5;

    public List<UserStorage> save(RequestSaveDto requestDto) {
        Member member = memberService.getCurrentMember();
        List<Long> ingredientIds = requestDto.getIngredientIds();
        List<Ingredient> ingredients = ingredientIds.stream()
                .map(ingredientService::getReferenceById) /*TODO:예외처리*/
                .toList();
        return requestDto.toEntities(member, ingredients);
    }

    public UserStorages update(RequestUpdateDto requestDto) {
        Member member = memberService.getCurrentMember();
        List<UserStorage> updatedUserStorages = new ArrayList<>();
        for (RequestUpdateOneDto requestOne : requestDto.getRequest()) {
            UserStorage userStorage = userStorageRepository.findByIdAndMember(requestOne.getId(), member);
            updatedUserStorages.add(userStorage.update(requestOne));
        }
        return new UserStorages(updatedUserStorages);
    }

    public ResponseDeleteDto delete(RequestDeleteDto requestDto) {
        Member member = memberService.getCurrentMember();
        List<UserStorage> userStorages = userStorageRepository.findByIdInAndMember(requestDto.getIds(), member);
        userStorages.forEach(BaseEntity::delete);
        return new ResponseDeleteDto(userStorages.size());
    }

    public UserStorages get() {
        Member currentMember = memberService.getCurrentMember();
        List<UserStorage> userStorages = userStorageRepository.findAllByMemberAndDeletedAtIsNull(currentMember);
        return new UserStorages(userStorages);
    }

    public UserStorages get(Pageable pageable) {
        Member member = memberService.getCurrentMember();
        List<UserStorage> userStorages = userStorageRepository.findByMemberAndDeletedAtIsNullOrderByIdDesc(member, pageable).getContent();
        return new UserStorages(userStorages);
    }

    public UserStorages findStorages(String type) {
        Member member = memberService.getCurrentMember();
        List<UserStorage> userStorages = userStorageRepository.findByMemberAndStorageType(member, StorageType.find(type));
        return new UserStorages(userStorages);
    }

    public List<ResponseGetNearExpiryDto> getNearExpiry() {
        Member currentMember = memberService.getCurrentMember();
        LocalDate indexDate = LocalDate.now().plusDays(WARNING_INDEX_DAY);
        List<UserStorage> userStorages = userStorageRepository.findAllByMemberAndDeletedAtIsNullAndExpirationDateBefore(currentMember, indexDate);
        UserStorages findNearExpiryUserStorages = new UserStorages(userStorages);
        return findNearExpiryUserStorages.toDtos(ResponseGetNearExpiryDto::new);
    }
}
