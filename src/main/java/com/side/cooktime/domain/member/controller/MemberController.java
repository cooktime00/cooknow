package com.side.cooktime.domain.member.controller;

import com.side.cooktime.domain.member.model.Admin;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.model.Role;
import com.side.cooktime.domain.member.model.User;
import com.side.cooktime.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;

    @ResponseBody
    @GetMapping(value = "/member/role")
    public String getMemberRole(@RequestParam("memberEmail") String memberEmail) {
        log.info("getMemberRole");
        Member member = memberService.findByEmail(memberEmail);

        if (member instanceof User) {
            return Role.USER.name();
        }
        if (member instanceof Admin) {
            return Role.ADMIN.name();
        }

        return "Not Member (" + memberEmail + ")";
    }

    //TODO: 아래는 권한 확인용... 삭제 예정
    
    @ResponseBody
    @GetMapping(value = "/member/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String getMemberMine(Authentication authentication) {
        log.info("getMemberMine");
        log.info("my Email : {}", authentication.getName());
        Member member = memberService.findByEmail(authentication.getName());

        if (member instanceof User) {
            return Role.USER.name();
        }
        if (member instanceof Admin) {
            return Role.ADMIN.name();
        }

        return "Not Member (" + authentication.getName() + ")";
    }

    @ResponseBody
    @GetMapping(value = "/member/{memberEmail}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getMember(Authentication authentication, @PathVariable("memberEmail") String memberEmail) {
        log.info("getMember");
        log.info("my Email : {}", authentication.getName());
        log.info("memberEmail : {}", memberEmail);
        Member member = memberService.findByEmail(memberEmail);

        if (member instanceof User) {
            return Role.USER.name();
        }
        if (member instanceof Admin) {
            return Role.ADMIN.name();
        }

        return "Not Member (" + memberEmail + ")";
    }
}
