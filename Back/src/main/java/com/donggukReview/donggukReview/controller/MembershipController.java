package com.donggukReview.donggukReview.controller;

import com.donggukReview.donggukReview.common.AuthUser;
import com.donggukReview.donggukReview.dto.RegisterRequestDto;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@Slf4j
public class MembershipController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(
			@RequestPart(value = "data", required = true) RegisterRequestDto requestDto,
			@RequestPart(value = "file", required = false) MultipartFile file
	) {
		if (userService.existsByUserId(requestDto.getUserId())) {
			return ResponseEntity.badRequest().body("이미 존재하는 회원입니다.");
		}

		long createdUserId = userService.register(requestDto, file);

		if (createdUserId == -1) {
			return ResponseEntity.internalServerError().body("회원 등록에 실패하였습니다.");
		}

		return ResponseEntity.created(URI.create("/user/" + createdUserId)).body("회원 등록에 성공하였습니다.");
	}

	@DeleteMapping("/withdraw")
	public ResponseEntity<?> register(@AuthUser Users userEntity) {
		if (userEntity == null) {
			return ResponseEntity.notFound().build();
		}

		userService.deleteUser(userEntity.getId());

		return ResponseEntity.ok(userEntity.getUserId() + " 회원님이 회원 탈퇴하였습니다.");
	}
}
