package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.entity.Review;
import com.donggukReview.donggukReview.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviewByUserId(long userId) {
        return reviewRepository.findByUserId(userId);
    }
}
