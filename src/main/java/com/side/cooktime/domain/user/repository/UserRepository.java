package com.side.cooktime.domain.user.repository;

import com.side.cooktime.domain.user.model.Email;
import com.side.cooktime.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default Optional<User> findByEmail(String email) {
        return findByEmail(new Email(email));
    }

    Optional<User> findByEmail(Email email);

    @Modifying
    @Query("UPDATE User u SET u.deletedAt = now() WHERE u.id = :id")
    void deleteById(Long id);

}
