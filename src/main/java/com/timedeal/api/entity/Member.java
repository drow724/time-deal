package com.timedeal.api.entity;

import java.util.ArrayList;
import java.util.List;

import com.timedeal.api.dto.MemberDto;
import com.timedeal.api.entity.base.Audit;
import com.timedeal.common.constant.Role;
import com.timedeal.common.util.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Audit {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private String password;
	
	private Role role;
	
	@OneToMany(mappedBy = "member")
	List<Order> orders = new ArrayList<>();
	
	@Column(columnDefinition = "VARCHAR(1) DEFAULT 'N'")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean delYn = Boolean.FALSE;
	
	public Member(MemberDto dto) {
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.role = dto.getRole();
	}

	public void delete() {
		this.delYn = Boolean.TRUE;
	}

}
