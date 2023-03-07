package com.timedeal.member.entity;

import com.timedeal.member.dto.MemberDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member  {

	@Id
	@GeneratedValue
	private Long id;
	
	private String email;
	
	private String delYn = "N";
	
	public Member(MemberDto dto) {
		this.email = dto.getEmail();
	}

	public void delete() {
		this.delYn = "Y";
	}

}
