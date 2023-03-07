package com.timedeal.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timedeal.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
