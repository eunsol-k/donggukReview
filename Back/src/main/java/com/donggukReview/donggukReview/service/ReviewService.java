package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.UserReviewListResponseDTO;
import com.donggukReview.donggukReview.dto.UserReviewRequestDTO;
import com.donggukReview.donggukReview.dto.UserReviewResponseDTO;
import com.donggukReview.donggukReview.entity.Ratings;
import com.donggukReview.donggukReview.entity.Review;
import com.donggukReview.donggukReview.entity.Users;
import com.donggukReview.donggukReview.repository.RatingsRepository;
import com.donggukReview.donggukReview.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private RatingsRepository ratingsRepository;

    // 마이 페이지 리뷰 목록 조회
    public UserReviewListResponseDTO getUserReviewList(Users users) {
        UserReviewListResponseDTO responseDTO = new UserReviewListResponseDTO();
        List<Review> reviewList = reviewRepository.findByUserIdOrderByIdDesc(users.getId());

        List<UserReviewResponseDTO> userReviewResponseDTOList = new ArrayList<>();
        for (Review review : reviewList) {
            UserReviewResponseDTO userReviewResponseDTO = new UserReviewResponseDTO();

            userReviewResponseDTO.setReviewId(review.getId());
            userReviewResponseDTO.setReviewContents(review.getReviewContents());
            userReviewResponseDTO.setReviewRatings(review.getReviewRatings());
            userReviewResponseDTO.setReviewRecommended(review.getReviewRecommended());
            userReviewResponseDTO.setCafeteriaId(review.getCafeteriaId());

            userReviewResponseDTOList.add(userReviewResponseDTO);
        }
        responseDTO.setCount(userReviewResponseDTOList.size());
        responseDTO.setUserReviewList(userReviewResponseDTOList);
        return responseDTO;
    }

    // 리뷰 작성
    public Review addReview(Users users, UserReviewRequestDTO requestDTO) {
        Review review = new Review();

        review.setReviewContents(requestDTO.getReviewContents());
        review.setReviewRatings(requestDTO.getReviewRatings());
        review.setReviewRecommended(0);
        review.setUserId(users.getId());
        review.setCafeteriaId(requestDTO.getCafeteriaId());

        Review savedReview = reviewRepository.save(review);

        // 해당 음식점의 첫 리뷰인 경우
        if (!ratingsRepository.existsByCafeteriaId(review.getCafeteriaId())) {
            Ratings ratings = new Ratings();
            ratings.setRatings(review.getReviewRatings());
            ratings.setCafeteriaId(review.getCafeteriaId());

            ratingsRepository.save(ratings);
        } else {
            updateCafeteriaRating(review);
        }
        return savedReview;
    }

    // 리뷰 수정
    public long updateReview(Long reviewId, UserReviewRequestDTO requestDTO) {
        Review review = reviewRepository.findById(reviewId).get();

        if (!requestDTO.getReviewContents().isBlank()) {
            review.setReviewContents(requestDTO.getReviewContents());
        }
        if (!requestDTO.getReviewRatings().isBlank()) {
            review.setReviewRatings(requestDTO.getReviewRatings());
        }

        try {
            reviewRepository.save(review);
            updateCafeteriaRating(review);

            return review.getId();
        } catch (Exception e) {
            return -1;
        }
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    // 리뷰 추천
    public void recommendReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();
        review.setReviewRecommended(review.getReviewRecommended() + 1);
        reviewRepository.save(review);
    }

    // 음식점 평점 계산
    private void updateCafeteriaRating(Review review) {
        List<Review> cafeteriaReviewList = reviewRepository.findByCafeteriaIdOrderByIdDesc(review.getCafeteriaId());

        // 평점 평균 계산
        double sum = 0.0;
        for (Review cafeteriaReview : cafeteriaReviewList) {
            sum = Double.parseDouble(cafeteriaReview.getReviewRatings());
        }
        sum = sum / cafeteriaReviewList.size();

        Ratings ratings = ratingsRepository.findByCafeteriaId(review.getCafeteriaId());
        ratings.setRatings(String.format("%.2f", sum));
        ratings.setCafeteriaId(review.getCafeteriaId());

        ratingsRepository.save(ratings);
    }

    public List<Review> getAllReviewByUserId(long userId) {
        return reviewRepository.findByUserIdOrderByIdDesc(userId);
    }

    public List<Review> getReviewByCafeteriaId(long cafeteriaId) {
        return reviewRepository.findByCafeteriaIdOrderByIdDesc(cafeteriaId);
    }

    public boolean isExistsReview(Long reviewId) {
        return reviewRepository.existsById(reviewId);
    }

    public boolean isWrittenByMe(Long reviewId, Long userId) {
        return reviewRepository.findByIdAndUserId(reviewId, userId).isPresent();
    }
}
