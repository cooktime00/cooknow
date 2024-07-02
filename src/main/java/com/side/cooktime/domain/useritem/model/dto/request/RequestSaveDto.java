package com.side.cooktime.domain.useritem.model.dto.request;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.domain.useritem.model.UserItem;
import lombok.Data;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class RequestSaveDto {
    private List<RequestSaveOneDto> request;    //TODO: 명칭 변경 예정 (request -> userItemList)

    public List<UserItem> toEntities(User user, List<Ingredient> ingredients) {
        return IntStream.range(0, request.size())
                .mapToObj(index -> getUserStorage(request.get(index), user, ingredients.get(index)))
                .toList();
    }

    private UserItem getUserStorage(RequestSaveOneDto requestOneDto, User user, Ingredient ingredient) {
        return requestOneDto.toEntity(user, ingredient);
    }

    public List<Long> getIngredientIds() {
        return request.stream()
                .map(RequestSaveOneDto::getIngredient_id)
                .toList();
    }
}
