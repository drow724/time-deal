package com.timedeal.member.service;

import java.util.Optional;

import com.timedeal.member.dto.MemberDto;
import com.timedeal.member.entity.Member;

public interface MemberService {

	public Member join(MemberDto dto);
	
	public Member delete(MemberDto dto);
	
	public Member login(MemberDto dto);
}
