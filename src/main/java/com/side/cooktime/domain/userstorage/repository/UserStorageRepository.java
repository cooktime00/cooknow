package com.side.cooktime.domain.userstorage.repository;

import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStorageRepository extends JpaRepository<UserStorage, Long> {
    List<UserStorage> findByIdInAndMember(List<Long> ids, Member member);
}
