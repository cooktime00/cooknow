package com.side.cooktime.domain.useritem.service;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.StorageType;
import com.side.cooktime.domain.ingredient.service.IngredientService;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.domain.user.service.UserService;
import com.side.cooktime.domain.useritem.model.dto.response.ResponseGetNearExpiryDto;
import com.side.cooktime.global.model.BaseEntity;
import com.side.cooktime.domain.useritem.model.UserItem;
import com.side.cooktime.domain.useritem.model.UserItems;
import com.side.cooktime.domain.useritem.model.dto.request.RequestDeleteDto;
import com.side.cooktime.domain.useritem.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.useritem.model.dto.request.RequestUpdateDto;
import com.side.cooktime.domain.useritem.model.dto.request.RequestUpdateOneDto;
import com.side.cooktime.domain.useritem.model.dto.response.ResponseDeleteDto;
import com.side.cooktime.domain.useritem.repository.UserItemRepository;
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
public class UserItemService {

    private final UserService userService;
    private final UserItemRepository userItemRepository;
    private final IngredientService ingredientService;

    private final long WARNING_INDEX_DAY = 5;

    public List<UserItem> save(RequestSaveDto requestDto) {
        User user = new User();
        List<Long> ingredientIds = requestDto.getIngredientIds();
        List<Ingredient> ingredients = ingredientIds.stream()
                .map(ingredientService::getReferenceById) /*TODO:예외처리*/
                .toList();
        return requestDto.toEntities(user, ingredients);
    }

    public UserItems update(RequestUpdateDto requestDto) {
        User user = new User();
        List<UserItem> updatedUserItems = new ArrayList<>();
        for (RequestUpdateOneDto requestOne : requestDto.getRequest()) {
            UserItem userItem = userItemRepository.findByIdAndUser(requestOne.getId(), user);
            updatedUserItems.add(userItem.update(requestOne));
        }
        return new UserItems(updatedUserItems);
    }

    public ResponseDeleteDto delete(RequestDeleteDto requestDto) {
        User user = new User();
        List<UserItem> userItems = userItemRepository.findByIdInAndUser(requestDto.getIds(), user);
        userItems.forEach(BaseEntity::delete);
        return new ResponseDeleteDto(userItems.size());
    }

    public UserItems get() {
        User user = new User();
        List<UserItem> userItems = userItemRepository.findAllByUserAndDeletedAtIsNull(user);
        return new UserItems(userItems);
    }

    public UserItems get(Pageable pageable) {
        User user = new User();
        List<UserItem> userItems = userItemRepository.findByUserAndDeletedAtIsNullOrderByIdDesc(user, pageable).getContent();
        return new UserItems(userItems);
    }

    public UserItems findStorages(String type) {
        User user = new User();
        List<UserItem> userItems = userItemRepository.findByUserAndStorageType(user, StorageType.find(type));
        return new UserItems(userItems);
    }

    public List<ResponseGetNearExpiryDto> getNearExpiry() {
        User user = new User();
        LocalDate indexDate = LocalDate.now().plusDays(WARNING_INDEX_DAY);
        List<UserItem> userItems = userItemRepository.findAllByUserAndDeletedAtIsNullAndExpirationDateBefore(user, indexDate);
        UserItems findNearExpiryUserItems = new UserItems(userItems);
        return findNearExpiryUserItems.toDtos(ResponseGetNearExpiryDto::new);
    }
}
