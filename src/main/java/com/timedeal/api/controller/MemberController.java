package com.timedeal.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timedeal.api.dto.LoginDto;
import com.timedeal.api.entity.Member;
import com.timedeal.api.http.request.MemberRequest;
import com.timedeal.api.http.response.MemberResponse;
import com.timedeal.api.service.MemberUseCase;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberUseCase memberUseCase;

	private final HttpSession session;
	
	@PostMapping("/login")
	public ResponseEntity<MemberResponse> login(@RequestBody MemberRequest request) {
		Member member = memberUseCase.login(request);
		session.setAttribute("login", new LoginDto(member));
		
		return ResponseEntity.ok(new MemberResponse(member));
	}

	@PostMapping("/join")
	public ResponseEntity<MemberResponse> join(@RequestBody MemberRequest request) {
		Member member = memberUseCase.join(request);
		session.setAttribute("login", new LoginDto(member));
		return ResponseEntity.ok(new MemberResponse(member));
	}

	@DeleteMapping
	public ResponseEntity<MemberResponse> delete(@RequestBody MemberRequest request) {
		Member member = memberUseCase.delete(request);
		session.removeAttribute("login");
		return ResponseEntity.ok(new MemberResponse(member));
	}

}
