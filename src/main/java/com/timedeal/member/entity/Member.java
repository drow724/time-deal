package com.timedeal.member.entity;

import com.timedeal.common.util.BooleanToYNConverter;
import com.timedeal.member.dto.MemberDto;
import com.timedeal.member.entity.base.Audit;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Audit {

	@Id
	@GeneratedValue
	private Long id;
	
	private String email;
	
	private String password;
	
	@Column(columnDefinition = "VARCHAR(1) DEFAULT 'N'")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean delYn = Boolean.FALSE;
	
	public Member(MemberDto dto) {
		this.email = dto.getEmail();
		this.password = dto.getPassword();
	}

	public void delete() {
		this.delYn = Boolean.TRUE;
	}

}
