package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.UserReviewListResponseDTO;
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
    public Review addReview(Review review) {
        Review savedReview = reviewRepository.save(review);

        if (!ratingsRepository.existsByCafeteriaId(review.getCafeteriaId())) {
            Ratings ratings = new Ratings();
            ratings.setRatings(review.getReviewRatings());
            ratings.setCafeteriaId(review.getCafeteriaId());

            ratingsRepository.save(ratings);
        } else {
            List<Review> cafeteriaReviewList = reviewRepository.findByCafeteriaIdOrderByIdDesc(review.getCafeteriaId());

            // TODO 평점 평균 계산 (작성 중)

            double sum = 0.0;
            for (Review cafeteriaReview : cafeteriaReviewList) {

            }

            Ratings ratings = ratingsRepository.findByCafeteriaId(review.getCafeteriaId());
            ratings.setRatings(review.getReviewRatings());
            ratings.setCafeteriaId(review.getCafeteriaId());

            ratingsRepository.save(ratings);
        }

        return savedReview;
    }

    public List<Review> getAllReviewByUserId(long userId) {
        return reviewRepository.findByUserIdOrderByIdDesc(userId);
    }
    public List<Review> getReviewByCafeteriaId(long cafeteriaId) {
        return reviewRepository.findByCafeteriaIdOrderByIdDesc(cafeteriaId);
    }
}
