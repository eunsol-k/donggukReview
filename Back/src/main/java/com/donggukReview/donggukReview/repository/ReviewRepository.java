package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserIdOrderByIdDesc(Long userId);
    List<Review> findByCafeteriaIdOrderByIdDesc(Long cafeteriaId);
}
