package com.side.cooknow.domain.useritem.model.dto.request;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.useritem.model.UserItem;
import lombok.Data;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class RequestSaveDto {
    private Long userId;
    private List<RequestSaveOneDto> items;

    public List<UserItem> toEntities(User user, List<Ingredient> ingredients) {
        return IntStream.range(0, this.items.size())
                .mapToObj(index -> getUserStorage(this.items.get(index), user, ingredients.get(index)))
                .toList();
    }

    private UserItem getUserStorage(RequestSaveOneDto requestOneDto, User user, Ingredient ingredient) {
        return requestOneDto.toEntity(user, ingredient);
    }

    public List<Long> getIngredientIds() {
        return items.stream()
                .map(RequestSaveOneDto::getId)
                .toList();
    }
}
