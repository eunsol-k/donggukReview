package com.donggukReview.donggukReview.controller;

import com.donggukReview.donggukReview.common.AuthUser;
import com.donggukReview.donggukReview.dto.*;
import com.donggukReview.donggukReview.entity.Review;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.service.CafeteriaService;
import com.donggukReview.donggukReview.service.LikeService;
import com.donggukReview.donggukReview.service.ReviewService;
import com.donggukReview.donggukReview.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

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
    @Autowired
    private ReviewService reviewService;

    // 유저 상세 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(
            @AuthUser Users userEntity,
            @PathVariable(value = "id") Long userId
    ) {
        if (!userService.existsById(userEntity.getId())) {
            return ResponseEntity.badRequest().body("존재하지 않는 회원입니다.");
        }

        if (!userEntity.getId().equals(userId)) {
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
            @PathVariable(value = "id") Long userId,
            @RequestPart(value = "data") UserInfoUpdateRequestDTO requestDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        if (!userService.existsById(userEntity.getId())) {
            return ResponseEntity.badRequest().body("존재하지 않는 회원입니다.");
        }

        if (!userEntity.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("본인의 마이 페이지만 수정할 수 있습니다.");
        }

        if ((!requestDto.getUserPrevPassword().isBlank() || !requestDto.getUserNextPassword().isBlank()) && !encoder.matches(requestDto.getUserPrevPassword(), userEntity.getUserPassword())) {
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
        return ResponseEntity.created(URI.create("/cafeteria/" + cafeteriaId)).body(cafeteriaId + " 음식점에 좋아요 표시했습니다.");
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

    // 마이 페이지 리뷰 목록 조회
    @GetMapping("/reviews")
    public ResponseEntity<?> getReviewList(@AuthUser Users users) {
        UserReviewListResponseDTO responseDTO = reviewService.getUserReviewList(users);
        return ResponseEntity.ok(responseDTO);
    }

    // 리뷰 작성
    @PostMapping("/reviews/{id}")
    public ResponseEntity<?> addReview(
            @AuthUser Users users,
            @RequestBody UserReviewRequestDTO requestDto,
            @PathVariable(value = "id") Long cafeteriaId
    ) {
        if(!cafeteriaService.isExistsCafeteria(cafeteriaId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 음식점입니다.");
        }

        Review savedReview = reviewService.addReview(users, requestDto);
        UserPostReviewResponseDTO responseDTO = new UserPostReviewResponseDTO(savedReview.getId());
        return ResponseEntity.created(URI.create("/user/reviews")).body(responseDTO);
    }

    // 리뷰 수정
    @PatchMapping("/reviews/{id}")
    public ResponseEntity<?> updateReview(
            @AuthUser Users users,
            @RequestBody UserReviewRequestDTO requestDto,
            @PathVariable(value = "id") Long reviewId
    ) {
        if(!reviewService.isExistsReview(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 리뷰입니다.");
        }

        if(!reviewService.isWrittenByMe(reviewId, users.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("본인의 리뷰만 수정할 수 있습니다.");
        }

        long updated = reviewService.updateReview(reviewId, requestDto);

        if (updated == -1) {
            return ResponseEntity.internalServerError().body("리뷰 수정에 실패했습니다.");
        }
        return ResponseEntity.ok("리뷰를 성공적으로 수정하였습니다.");
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(
            @AuthUser Users users,
            @PathVariable(value = "id") Long reviewId
    ) {
        if(!reviewService.isExistsReview(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 리뷰입니다.");
        }

        if(!reviewService.isWrittenByMe(reviewId, users.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("본인의 리뷰만 삭제할 수 있습니다.");
        }

        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(reviewId + " 리뷰를 삭제하였습니다.");
    }

    // 리뷰 추천
    @PostMapping("/reviews/{id}/recommend")
    public ResponseEntity<?> recommendReview(
            @AuthUser Users users,
            @PathVariable(value = "id") Long reviewId
    ) {
        if(!reviewService.isExistsReview(reviewId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("존재하지 않는 리뷰입니다.");
        }

        if(reviewService.isWrittenByMe(reviewId, users.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("본인의 리뷰는 추천할 수 없습니다.");
        }

        reviewService.recommendReview(reviewId);
        return ResponseEntity.ok(reviewId + " 리뷰에 추천했습니다.");
    }
}
