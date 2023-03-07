package com.timedeal.member.dto.login;

import java.io.Serializable;

import com.timedeal.member.entity.Member;

import lombok.Getter;

@Getter
public class LoginDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String email;
	
	public LoginDto(Member member) {
		this.email = member.getEmail();
	}
}
