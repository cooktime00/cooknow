package com.side.cooknow.domain.ingredient.repository;

import com.side.cooknow.domain.ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
