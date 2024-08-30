package com.side.cooknow.domain.useritem.repository;

import com.side.cooknow.domain.ingredient.model.StorageType;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.useritem.model.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    List<UserItem> findAllByIdInAndDeletedAtIsNull(List<Long> ids);

    Optional<UserItem> findByIdAndDeletedAtIsNull(Long id);

    List<UserItem> findAllByUserAndDeletedAtIsNull(User user);

    List<UserItem> findAllByUserAndDeletedAtIsNullAndExpirationDateBefore(User user, LocalDate date);

}
