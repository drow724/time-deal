package com.timedeal.api.service;

import com.timedeal.api.entity.Member;
import com.timedeal.api.http.request.MemberRequest;

public interface MemberUseCase {

	public Member join(MemberRequest request);
	
	public Member delete(MemberRequest request);
	
	public Member login(MemberRequest request);
}
