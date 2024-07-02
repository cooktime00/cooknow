package com.side.cooktime.domain.useritem.model;

import com.side.cooktime.domain.ingredient.model.Ingredient;
import com.side.cooktime.domain.ingredient.model.StorageType;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.global.model.BaseEntity;
import com.side.cooktime.domain.useritem.model.dto.request.RequestUpdateOneDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@Table(name = "user_item")
public class UserItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "storage_type")
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    public UserItem update(RequestUpdateOneDto requestOne) {
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
