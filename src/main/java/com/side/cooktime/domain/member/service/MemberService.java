package com.side.cooktime.domain.member.service;

import com.side.cooktime.config.auth.OAuth2UserUtils;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Not Found"));
    }

    public Member getCurrentMember() {
        return findByEmail(OAuth2UserUtils.getEmail(SecurityContextHolder.getContext().getAuthentication()));
    }

}
