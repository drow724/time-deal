package com.timedeal.api.dto;

import com.timedeal.common.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

	private String email;
	
	private String password;
	
	private Role role = Role.USER;
}
