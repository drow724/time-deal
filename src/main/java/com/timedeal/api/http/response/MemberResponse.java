package com.timedeal.api.http.response;

import com.timedeal.api.entity.Member;
import com.timedeal.common.constant.Role;

import lombok.Getter;

@Getter
public class MemberResponse {
	
	private final Long id;
	
	private final String email;
	
	private final Role role;
	
	public MemberResponse(Member member) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.role = member.getRole();
	}

}
