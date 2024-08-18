package com.side.cooknow.domain.useritem.service;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.StorageType;
import com.side.cooknow.domain.ingredient.service.IngredientService;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.useritem.exception.UserItemErrorCode;
import com.side.cooknow.domain.useritem.exception.UserItemException;
import com.side.cooknow.domain.useritem.model.dto.request.*;
import com.side.cooknow.domain.useritem.model.dto.response.ResponseFindDto;
import com.side.cooknow.domain.useritem.model.dto.response.ResponseGetNearExpiryDto;
import com.side.cooknow.global.config.auth.AuthenticationFacade;
import com.side.cooknow.global.model.BaseEntity;
import com.side.cooknow.domain.useritem.model.UserItem;
import com.side.cooknow.domain.useritem.model.UserItems;
import com.side.cooknow.domain.useritem.model.dto.response.ResponseDeleteDto;
import com.side.cooknow.domain.useritem.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class UserItemService {

    private final UserItemRepository userItemRepository;
    private final IngredientService ingredientService;
    private final AuthenticationFacade authenticationFacade;

    private final static int WARNING_INDEX_DAY = 5;

    public List<UserItem> save(RequestSaveDto requestDto) {
        User user = getAuthenticatedUser();
        List<UserItem> userItems = toUserItems(requestDto.getItems(), user);
        return userItemRepository.saveAll(userItems);
    }

    public UserItems update(RequestUpdateDto requestDto) {
        List<UserItem> userItems = requestDto.getItems().stream()
                .map(this::updateUserItem)
                .collect(Collectors.toList());
        userItemRepository.saveAll(userItems);
        return new UserItems(userItems);
    }

    public ResponseDeleteDto delete(RequestDeleteDto requestDto) {
        List<UserItem> userItems = userItemRepository.findAllByIdInAndDeletedAtIsNull(requestDto.getId());
        userItems.forEach(BaseEntity::delete);
        userItemRepository.saveAll(userItems);
        return new ResponseDeleteDto(userItems.size());
    }

    @Transactional(readOnly = true)
    public List<ResponseFindDto> find(Long userId, String type, Locale locale) {
        User user = getAuthenticatedUser();
        StorageType storageType = StorageType.find(type);
        List<UserItem> userItemList = userItemRepository.findAllByUserAndStorageType(user, storageType);
        UserItems userItems = new UserItems(userItemList);
        return userItems.toDtos(ResponseFindDto::new, locale);
    }

    @Transactional(readOnly = true)
    public UserItems getNearExpiry() {
        User user = getAuthenticatedUser();
        LocalDate indexDate = LocalDate.now().plusDays(WARNING_INDEX_DAY);
        List<UserItem> userItems = userItemRepository.findAllByUserAndDeletedAtIsNullAndExpirationDateBefore(user, indexDate);
        return new UserItems(userItems);
    }

    private User getAuthenticatedUser() {
        return authenticationFacade.getAuthenticatedUser();
    }

    private UserItem updateUserItem(RequestUpdateOneDto requestOne) {
        UserItem userItem = userItemRepository.findByIdAndDeletedAtIsNull(requestOne.getId())
                .orElseThrow(() -> new UserItemException(UserItemErrorCode.USER_ITEM_NOT_FOUND));
        return userItem.update(requestOne);
    }

    private List<UserItem> toUserItems(List<RequestSaveOneDto> items, User user) {
        return items.stream().map(dto -> {
            Ingredient ingredient = ingredientService.getReferenceById(dto.getId());
            return UserItem.builder()
                    .user(user)
                    .ingredient(ingredient)
                    .quantity(dto.getQuantity())
                    .expirationDate(dto.getExpirationDate())
                    .storageType(StorageType.find(dto.getStorageType()))
                    .build();
        }).collect(Collectors.toList());
    }
}
