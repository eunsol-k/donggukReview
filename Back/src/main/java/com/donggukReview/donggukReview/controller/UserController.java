package com.donggukReview.donggukReview.controller;

import com.donggukReview.donggukReview.common.AuthUser;
import com.donggukReview.donggukReview.dto.RegisterRequestDTO;
import com.donggukReview.donggukReview.dto.UserInfoResponseDTO;
import com.donggukReview.donggukReview.dto.UserInfoUpdateRequestDTO;
import com.donggukReview.donggukReview.dto.UserLikeListResponseDTO;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.service.CafeteriaService;
import com.donggukReview.donggukReview.service.LikeService;
import com.donggukReview.donggukReview.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CafeteriaService cafeteriaService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    // 유저 상세 정보 조회
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

    // 유저 상세 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserInfo(
            @AuthUser Users userEntity,
            @PathVariable(value = "id") String userId,
            @RequestPart(value = "data") UserInfoUpdateRequestDTO requestDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        if (!userService.existsByUserId(userId)) {
            return ResponseEntity.badRequest().body("존재하지 않는 회원입니다.");
        }

        if (!userEntity.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("본인의 마이 페이지만 수정할 수 있습니다.");
        }

        if (!encoder.matches(requestDto.getUserPrevNickname(), userEntity.getUserPassword())) {
            return ResponseEntity.badRequest().body("비밀번호를 다시 확인하세요.");
        }

        long updatedUserId = userService.updateUserInfo(userEntity, requestDto, file);

        if (updatedUserId != userEntity.getId() || updatedUserId == -1) {
            return ResponseEntity.internalServerError().body("회원 정보 수정에 실패하였습니다.");
        }

        return ResponseEntity.ok("회원 정보 수정에 성공하였습니다.");
    }

    // 유저 좋아요 리스트 조회
    @GetMapping("/likes")
    public ResponseEntity<?> getLikeList(@AuthUser Users users) {
        UserLikeListResponseDTO responseDTO = userService.getUserLikeList(users);
        return ResponseEntity.ok(responseDTO);
    }

    // 좋아요 표시하기
    @PostMapping("/likes/{id}")
    public ResponseEntity<?> checkLike(
            @AuthUser Users users,
            @PathVariable(value = "id") Long cafeteriaId
    ) {
        if(!cafeteriaService.isExistsCafeteria(cafeteriaId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 음식점입니다.");
        }

        if(likeService.isExistsLike(users, cafeteriaId)) {
            return ResponseEntity.badRequest().body("이미 좋아요 표시한 음식점입니다.");
        }

        likeService.checkLike(users, cafeteriaId);
        return ResponseEntity.ok(cafeteriaId + " 음식점에 좋아요 표시했습니다.");
    }

    // 좋아요 해제하기
    @DeleteMapping("/likes/{id}")
    public ResponseEntity<?> unCheckLike(
            @AuthUser Users users,
            @PathVariable(value = "id") Long cafeteriaId
    ) {
        if(!cafeteriaService.isExistsCafeteria(cafeteriaId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 음식점입니다.");
        }

        if(!likeService.isExistsLike(users, cafeteriaId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("좋아요 표시하지 않은 음식점입니다.");
        }

        likeService.unCheckLike(users, cafeteriaId);
        return ResponseEntity.ok(cafeteriaId + " 음식점의 좋아요를 제거했습니다.");
    }
}
