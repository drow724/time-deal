package com.timedeal.member.service;

import org.springframework.stereotype.Service;

import com.timedeal.member.entity.Member;
import com.timedeal.member.repository.MemberRepository;
import com.timedeal.service.CrudService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements CrudService<Member> {

	private final MemberRepository memberRepository;
	
	@Override
	public Member save(Member member) {
		return memberRepository.save(member);
	}

}
