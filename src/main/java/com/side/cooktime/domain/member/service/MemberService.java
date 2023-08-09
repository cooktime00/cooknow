package com.side.cooktime.domain.member.service;

import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

}
