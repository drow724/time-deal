package com.timedeal.api.dto;

import java.io.Serializable;

import com.timedeal.api.entity.Member;
import com.timedeal.common.constant.Role;

import lombok.Getter;

@Getter
public class LoginDto implements Serializable {

	private static final long serialVersionUID = -2314912121387126592L;

	private Long id;
	
	private String email;
	
	private Role role;
	
	public LoginDto(Member member) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.role = member.getRole();
	}

	public LoginDto(Object attribute) throws IllegalAccessException {
		if(attribute instanceof LoginDto) {
			LoginDto dto = (LoginDto) attribute;
			this.id = dto.getId();
			this.email = dto.getEmail();
			this.role = dto.getRole();
		} else {
			throw new IllegalAccessException("로그인이 필요한 서비스입니다.");
		}
			
	}
}
