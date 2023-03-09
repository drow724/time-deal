package com.timedeal.member.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@PostMapping("/login")
	public Member login(@RequestBody MemberDto dto, HttpSession session) {		
		return memberService.login(dto);
	}

	@PostMapping("/join")
	public Member join(@RequestBody MemberDto dto, HttpSession session) {
		Member member = memberService.join(dto);
		session.setAttribute(member.getEmail(), new LoginDto(member));
		return member;
	}

	@DeleteMapping
	public Member delete(@RequestBody MemberDto dto, HttpSession session) {
		Member member = memberService.delete(dto);
		session.removeAttribute(member.getEmail());
		return member;
	}

}
