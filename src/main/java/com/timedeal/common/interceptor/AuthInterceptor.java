package com.timedeal.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.timedeal.api.dto.LoginDto;
import com.timedeal.common.constant.Role;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {
	@Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
       
		HttpSession session = request.getSession();
		
        if (session == null) {
        	throw new IllegalAccessException("로그인이 필요한 서비스입니다.");
        }

        LoginDto member = (LoginDto) session.getAttribute("login");
        
        if (member == null) {
        	throw new IllegalAccessException("로그인이 필요한 서비스입니다.");
        }

        if(member.getRole() != Role.ADMIN) {
        	throw new IllegalAccessException("요청권한이 없습니다.");
        }
        
        return true;
    }

}
