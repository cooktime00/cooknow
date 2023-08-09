package com.side.cooktime.domain.userstorage.model.dto.request;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import lombok.Data;

import java.util.List;
import java.util.stream.IntStream;

@Data
public class RequestSaveDto {
    private List<RequestSaveOneDto> requests;

    public List<UserStorage> toEntities(Member member, List<Ingredient> ingredients) {
        return IntStream.range(0, requests.size())
                .mapToObj(index -> getUserStorage(requests.get(index), member, ingredients.get(index)))
                .toList();
    }

    private UserStorage getUserStorage(RequestSaveOneDto requestOneDto, Member member, Ingredient ingredient) {
        return requestOneDto.toEntity(member, ingredient);
    }

    public List<Long> getIngredientIds() {
        return requests.stream()
                .map(RequestSaveOneDto::getIngredient_id)
                .toList();
    }
}
