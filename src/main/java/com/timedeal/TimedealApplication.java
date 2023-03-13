package com.timedeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import com.timedeal.api.entity.Member;
import com.timedeal.api.http.request.MemberRequest;
import com.timedeal.api.repository.MemberRepository;
import com.timedeal.common.constant.Role;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class TimedealApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimedealApplication.class, args);
	}

	private final MemberRepository memberRepository;
	
	@Profile("local")
	@PostConstruct
	public void init() {

		MemberRequest dto = new MemberRequest();
		dto.setEmail("admin");
		dto.setPassword("admin123");
		dto.setRole(Role.ADMIN);
		memberRepository.save(new Member(dto));
	}
}
