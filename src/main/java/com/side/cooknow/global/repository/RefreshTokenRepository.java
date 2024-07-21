package com.side.cooknow.global.repository;

import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.model.security.RefreshToken;
import com.side.cooknow.global.model.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteByUser(User user);

    boolean existsByTokenAndDeletedAtIsNull(Token token);

    Optional<RefreshToken> findByTokenAndDeletedAtIsNull(Token token);

    @Modifying
    @Query("UPDATE RefreshToken r SET r.deletedAt = CURRENT_TIMESTAMP WHERE r.user = :user")
    void updateEmailByUser(@Param("user") User user);
}
