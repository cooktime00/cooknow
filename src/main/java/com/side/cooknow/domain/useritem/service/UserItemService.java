package com.side.cooknow.domain.useritem.service;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.StorageType;
import com.side.cooknow.domain.ingredient.service.IngredientService;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.user.service.UserService;
import com.side.cooknow.domain.useritem.exception.UserItemErrorCode;
import com.side.cooknow.domain.useritem.exception.UserItemException;
import com.side.cooknow.domain.useritem.model.dto.request.*;
import com.side.cooknow.global.model.BaseEntity;
import com.side.cooknow.domain.useritem.model.UserItem;
import com.side.cooknow.domain.useritem.repository.UserItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
@Log4j2
public class UserItemService {

    private final UserItemRepository userItemRepository;
    private final UserService userService;
    private final IngredientService ingredientService;

    private final static int WARNING_INDEX_DAY = 5;

    public List<UserItem> save(Long userId, RequestSaveDto requestDto) {
        User user = userService.findById(userId);

        List<UserItem> userItemList = requestDto.getItemList().stream()
                .map(item -> {
                    Ingredient ingredient = ingredientService.findById(item.getIngredientId());
                    return UserItem.builder()
                            .user(user)
                            .ingredient(ingredient)
                            .quantity(item.getQuantity())
                            .expirationDate(item.getExpirationDate())
                            .storageType(StorageType.find(item.getStorageType()))
                            .build();
                })
                .collect(Collectors.toList());
        return userItemRepository.saveAll(userItemList);
    }

    public List<UserItem> update(Long userId, RequestUpdateDto requestDto) {
        User user = userService.findById(userId);
        List<UserItem> userItems = requestDto.getItemList().stream()
                .map(this::updateUserItem)
                .collect(Collectors.toList());
        userItemRepository.saveAll(userItems);
        return userItems;
    }

    public List<Long> delete(RequestDeleteDto requestDto) {
        List<UserItem> userItems = userItemRepository.findAllByIdInAndDeletedAtIsNull(requestDto.getIds());
        userItems.forEach(BaseEntity::delete);
        userItemRepository.saveAll(userItems);
        return userItems.stream().map(UserItem::getId).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserItem> find(Long userId) {
        User user = userService.findById(userId);
        return userItemRepository.findAllByUserAndDeletedAtIsNull(user);
    }

    @Transactional(readOnly = true)
    public List<UserItem> getNearExpiry(final Long userId) {
        User user = userService.findById(userId);
        LocalDate indexDate = LocalDate.now().plusDays(WARNING_INDEX_DAY);
        return userItemRepository.findAllByUserAndDeletedAtIsNullAndExpirationDateBefore(user, indexDate);
    }

    private UserItem updateUserItem(RequestUpdateOneDto requestOne) {
        UserItem userItem = userItemRepository.findByIdAndDeletedAtIsNull(requestOne.getId())
                .orElseThrow(() -> new UserItemException(UserItemErrorCode.USER_ITEM_NOT_FOUND));
        return userItem.update(requestOne);
    }
}
