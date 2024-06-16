package com.side.cooktime.domain.userstorage.model;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.StorageType;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.global.model.BaseEntity;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestUpdateOneDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "user_storage")   //TODO: UserStorage -> UserItem 으로 도메인 명 변경 예정
public class UserStorage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "storage_type")
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    public UserStorage update(RequestUpdateOneDto requestOne) {
        this.quantity = requestOne.getQuantity();
        this.expirationDate = requestOne.getExpiration_date();
        this.storageType = requestOne.getEnumStorageType();
        return this;
    }

    public String getIngredientName(){
        return ingredient.getKorName();
    }

    public String getIngredientImage(){
        return ingredient.getImageUrl();
    }
}
