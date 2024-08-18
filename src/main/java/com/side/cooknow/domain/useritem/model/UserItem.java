package com.side.cooknow.domain.useritem.model;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import com.side.cooknow.domain.ingredient.model.StorageType;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.model.BaseEntity;
import com.side.cooknow.domain.useritem.model.dto.request.RequestUpdateOneDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Locale;

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

    public UserItem (User user){
        this.user = user;
    }

    public UserItem update(RequestUpdateOneDto requestOne) {
        this.quantity = requestOne.getQuantity();
        this.expirationDate = requestOne.getExpirationDate();
        this.storageType = requestOne.getEnumStorageType();
        return this;
    }

    public String getIngredientName(Locale locale){
        return ingredient.getName(locale);
    }

    public String getIngredientImage(){
        return ingredient.getImageUrl();
    }
}
