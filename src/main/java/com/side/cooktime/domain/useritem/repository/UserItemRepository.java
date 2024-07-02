package com.side.cooktime.domain.useritem.repository;

import com.side.cooktime.domain.ingredient.model.StorageType;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.domain.useritem.model.UserItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    List<UserItem> findByIdInAndUser(List<Long> ids, User user);

    UserItem findByIdAndUser(Long id, User user);

    List<UserItem> findAllByUserAndDeletedAtIsNull(User user);

    List<UserItem> findByUserAndStorageType(User user, StorageType storageType);

    Page<UserItem> findByUserAndDeletedAtIsNullOrderByIdDesc(User user, Pageable pageable);

    List<UserItem> findAllByUserAndDeletedAtIsNullAndExpirationDateBefore(User user, LocalDate date);

}
