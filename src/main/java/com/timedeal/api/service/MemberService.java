package com.timedeal.api.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timedeal.api.entity.Member;
import com.timedeal.api.http.request.MemberRequest;
import com.timedeal.api.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements MemberUseCase {

	private final MemberRepository memberRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Member login(MemberRequest request) {
		Optional<Member> member = memberRepository.findByEmailAndPasswordAndDelYnFalse(request.getEmail(), request.getPassword());
		if(member.isEmpty()) {
			if(memberRepository.findByEmailAndDelYnFalse(request.getEmail()).isPresent()) {
				throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
			}
			throw new NoSuchElementException("존재하지 않는 회원입니다.");
		}
		return member.get();
	}

	@Override
	public Member join(MemberRequest request) {
		if(memberRepository.findByEmailAndDelYnFalse(request.getEmail()).isPresent()) {
			throw new DuplicateKeyException(request.getEmail() + " 중복된 이메일입니다.");
		}
		
		return memberRepository.save(new Member(request));
	}

	@Override
	public Member delete(MemberRequest request) {
		Member member = memberRepository.findByEmailAndDelYnFalse(request.getEmail()).orElseThrow(() -> new NoSuchElementException());
		member.delete();
		return member;
	}
	
}