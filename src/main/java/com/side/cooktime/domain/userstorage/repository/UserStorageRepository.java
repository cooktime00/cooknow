package com.side.cooktime.domain.userstorage.repository;

import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.userstorage.model.UserStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStorageRepository extends JpaRepository<UserStorage, Long> {
    List<UserStorage> findByIdInAndMember(List<Long> ids, Member member);

    UserStorage findByIdAndMember(Long id, Member member);

    List<UserStorage> findAllByMemberAndDeletedAtIsNull(Member member);

    Page<UserStorage> findByMemberAndDeletedAtIsNullOrderByIdDesc(Member member, Pageable pageable);
}
