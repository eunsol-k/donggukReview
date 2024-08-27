package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserIdOrderByIdDesc(Long userId);
    List<Review> findByCafeteriaIdOrderByIdDesc(Long cafeteriaId);
    Optional<Review> findByIdAndUserId(Long id, Long userId);
}
