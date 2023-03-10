package com.timedeal.api.service;

import com.timedeal.api.dto.MemberDto;
import com.timedeal.api.entity.Member;

public interface MemberService {

	public Member join(MemberDto dto);
	
	public Member delete(MemberDto dto);
	
	public Member login(MemberDto dto);
}
