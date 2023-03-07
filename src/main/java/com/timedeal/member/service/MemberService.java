package com.timedeal.member.service;

import org.springframework.stereotype.Service;

import com.timedeal.common.service.CrudService;
import com.timedeal.member.entity.Member;
import com.timedeal.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements CrudService<Member> {

	private final MemberRepository memberRepository;
	
	@Override
	public Member save(Member member) {
		return memberRepository.save(member);
	}

	@Override
	public Member delete(Member member) {
		member = memberRepository.findByEmail(member.getEmail());
		member.delete();
		return member;
	}

}
