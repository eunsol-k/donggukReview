package com.donggukReview.donggukReview.controller;

import com.donggukReview.donggukReview.common.AuthUser;
import com.donggukReview.donggukReview.dto.UserInfoResponseDTO;
import com.donggukReview.donggukReview.entity.Image;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.service.ImageService;
import com.donggukReview.donggukReview.service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(
            @AuthUser Users userEntity,
            @PathVariable(value = "id") String userId
    ) {
        if (!userService.existsByUserId(userId)) {
            return ResponseEntity.badRequest().body("존재하지 않는 회원입니다.");
        }

        if (!userEntity.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("본인의 마이 페이지만 확인할 수 있습니다.");
        }

        UserInfoResponseDTO responseDTO = userService.getUserInfo(userEntity);
        return ResponseEntity.ok(responseDTO);
    }
}
