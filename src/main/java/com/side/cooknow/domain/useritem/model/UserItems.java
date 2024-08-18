package com.side.cooknow.domain.useritem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Embeddable
public class UserItems {
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserItem> userItems = new ArrayList<>();

    public UserItems(List<UserItem> userItems){
        this.userItems = userItems;
    }

    public int getSize(){
        return this.userItems.size();
    }

    public <T> List<T> toDtos(BiFunction<UserItem, Locale, T> mapper, Locale locale) {
        return userItems.stream()
                .map(userItem -> mapper.apply(userItem, locale))
                .collect(Collectors.toList());
    }

    public <T> List<T> toDtos(Function<UserItem, T> mapper){
        return userItems.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
