package com.timedeal.member.entity;

import com.timedeal.member.dto.MemberDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member  {

	@Id
	@GeneratedValue
	private Long id;
	
	private String email;
	
	public Member(MemberDto dto) {
		this.email = dto.getEmail();
	}

}
