package com.side.cooktime.domain.userstorage.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Embeddable
public class UserStorages {
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserStorage> userStorages = new ArrayList<>();

    public UserStorages(List<UserStorage> userStorages){
        this.userStorages = userStorages;
    }

    public int getSize(){
        return this.userStorages.size();
    }

    public <T> List<T> toDtos(Function<UserStorage, T> mapper){
        return userStorages.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
