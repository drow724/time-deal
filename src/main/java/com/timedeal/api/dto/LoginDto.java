package com.timedeal.api.dto;

import com.timedeal.api.entity.Member;
import com.timedeal.common.constant.Role;

import lombok.Getter;

@Getter
public class LoginDto {

	private String email;
	
	private String password;
	
	private Role role;
	
	public LoginDto(Member member) {
		this.email = member.getEmail();
		this.password = member.getPassword();
	}
}
