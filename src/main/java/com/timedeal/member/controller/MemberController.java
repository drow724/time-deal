package com.timedeal.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timedeal.member.dto.MemberDto;
import com.timedeal.member.dto.login.LoginDto;
import com.timedeal.member.entity.Member;
import com.timedeal.member.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	@PostMapping
	public Member join(@RequestBody MemberDto dto, HttpSession session) {
		Member member = memberService.save(new Member(dto));
		session.setAttribute(member.getEmail(), new LoginDto(member));
		return member;
	}
	
}
