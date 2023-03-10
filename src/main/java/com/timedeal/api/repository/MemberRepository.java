package com.timedeal.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timedeal.api.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmailAndDelYnFalse(String email);

	Optional<Member> findByEmailAndPasswordAndDelYnFalse(String email, String password);

}
