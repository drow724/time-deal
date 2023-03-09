package com.timedeal.member.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.member.dto.MemberDto;
import com.timedeal.member.entity.Member;
import com.timedeal.member.repository.MemberRepository;
import com.timedeal.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Member login(MemberDto dto) {
		Optional<Member> member = memberRepository.findByEmailAndPasswordAndDelYnFalse(dto.getEmail(), dto.getPassword());
		if(member.isEmpty()) {
			if(memberRepository.findByEmailAndDelYnFalse(dto.getEmail()).isPresent()) {
				throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
			}
			throw new NoSuchElementException("존재하지 않는 회원입니다.");
		}
		return member.get();
	}

	@Override
	public Member join(MemberDto dto) {
		if(memberRepository.findByEmailAndDelYnFalse(dto.getEmail()).isPresent()) {
			throw new DuplicateKeyException(dto.getEmail() + " 중복된 이메일입니다.");
		}
		
		return memberRepository.save(new Member(dto));
	}

	@Override
	public Member delete(MemberDto dto) {
		Member member = memberRepository.findByEmailAndDelYnFalse(dto.getEmail()).orElseThrow(() -> new NoSuchElementException());
		member.delete();
		return member;
	}
	
}