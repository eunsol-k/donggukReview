package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.EntityDTO.ReviewDTO;
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
        List<Review> reviewList = reviewRepository.findByUserId(users.getId());

        List<UserReviewResponseDTO> userReviewResponseDTOList = new ArrayList<>();
        for (Review review : reviewList) {
            UserReviewResponseDTO userReviewResponseDTO = new UserReviewResponseDTO();

            userReviewResponseDTO.setReviewId(review.getId());
            userReviewResponseDTO.setReviewContents(review.getReviewContents());
//            userReviewResponseDTO.setReviewRatingsService(review.getReviewRatingsService());
//            userReviewResponseDTO.setReviewRatingsPrice(review.getReviewRatingsPrice());
//            userReviewResponseDTO.setReviewRatingsFlavor(review.getReviewRatingsFlavor());
            userReviewResponseDTO.setReviewRatingsTotal(review.getReviewRatings());
            userReviewResponseDTO.setReviewRecommended(review.getReviewRecommended());
            userReviewResponseDTO.setCafeteriaId(review.getCafeteriaId());

            userReviewResponseDTOList.add(userReviewResponseDTO);
        }
        responseDTO.setTotal(userReviewResponseDTOList.size());
        responseDTO.setUserReviewList(userReviewResponseDTOList);
        return responseDTO;
    }

    // 리뷰 작성
    public Review addReview(Review review) {
        Review savedReview = reviewRepository.save(review);

        if (!ratingsRepository.existsByCafeteriaId(review.getCafeteriaId())) {
            Ratings ratings = new Ratings();

//            ratings.setRatingsService(review.getReviewRatingsService());
//            ratings.setRatingsPrice(review.getReviewRatingsPrice());
//            ratings.setRatingsFlavor(review.getReviewRatingsFlavor());
            ratings.setRatings(review.getReviewRatings());
            ratings.setCafeteriaId(review.getCafeteriaId());

            ratingsRepository.save(ratings);
        } else {
            List<Review> cafeteriaReviewList = reviewRepository.findByCafeteriaId(review.getCafeteriaId());

            // TODO 평점 평균 계산 (작성 중)

            double serviceSum = 0.0;
            double priceSum = 0.0;
            double flavorSum = 0.0;
            double totalSum = 0.0;
            for (Review cafeteriaReview : cafeteriaReviewList) {

            }

            Ratings ratings = ratingsRepository.findByCafeteriaId(review.getCafeteriaId());

//            ratings.setRatingsService(review.getReviewRatingsService());
//            ratings.setRatingsPrice(review.getReviewRatingsPrice());
//            ratings.setRatingsFlavor(review.getReviewRatingsFlavor());
            ratings.setRatings(review.getReviewRatings());
            ratings.setCafeteriaId(review.getCafeteriaId());

            ratingsRepository.save(ratings);
        }

        return savedReview;
    }

    public List<Review> getAllReviewByUserId(long userId) {
        return reviewRepository.findByUserId(userId);
    }
    public List<Review> getReviewByCafeteriaId(long cafeteriaId) {
        return reviewRepository.findByCafeteriaId(cafeteriaId);
    }
}
