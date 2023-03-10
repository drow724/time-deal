package com.timedeal.api.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timedeal.api.dto.LoginDto;
import com.timedeal.api.dto.MemberDto;
import com.timedeal.api.entity.Member;
import com.timedeal.api.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/login")
	public Member login(@RequestBody MemberDto dto,HttpServletRequest request, HttpSession session) {
		Member member = memberService.login(dto);
		session.setAttribute("login", new LoginDto(member));
		return member;
	}

	@PostMapping("/join")
	public Member join(@RequestBody MemberDto dto, HttpSession session) {
		Member member = memberService.join(dto);
		session.setAttribute("login", new LoginDto(member));
		return member;
	}

	@DeleteMapping
	public Member delete(@RequestBody MemberDto dto, HttpSession session) {
		Member member = memberService.delete(dto);
		session.removeAttribute("login");
		return member;
	}

}
