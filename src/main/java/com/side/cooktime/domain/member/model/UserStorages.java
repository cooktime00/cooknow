package com.side.cooktime.domain.member.model;

import com.side.cooktime.domain.userstorage.model.UserStorage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Embeddable
public class UserStorages {
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<UserStorage> userStorages = new ArrayList<>();


}
