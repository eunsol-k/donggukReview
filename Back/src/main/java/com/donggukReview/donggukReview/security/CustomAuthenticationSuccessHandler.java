package com.donggukReview.donggukReview.security;

import java.io.IOException;

import com.donggukReview.donggukReview.common.JwtUtils;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Users userEntity = userRepository.findByUserId(userDetails.getUsername());

		String jwtToken = jwtUtils.createAccessToken(userEntity);

		// 응답 헤더에 생성한 토큰을 설정
		response.setHeader("token", jwtToken); // JWT 값
		response.setHeader("username", userEntity.getId().toString()); // Users: ID 값(PK값)
	}
}