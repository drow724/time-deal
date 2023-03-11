package com.timedeal.api.http.request;

import com.timedeal.common.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {

	private String email;
	
	private String password;
	
	private Role role = Role.USER;
}
