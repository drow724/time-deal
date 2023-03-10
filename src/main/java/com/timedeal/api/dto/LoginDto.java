package com.timedeal.api.dto;

import java.io.Serializable;

import com.timedeal.api.entity.Member;
import com.timedeal.common.constant.Role;

import lombok.Getter;

@Getter
public class LoginDto implements Serializable{

	private static final long serialVersionUID = -2314912121387126592L;

	private String email;
	
	private String password;
	
	private Role role;
	
	public LoginDto(Member member) {
		this.email = member.getEmail();
		this.password = member.getPassword();
	}
}
