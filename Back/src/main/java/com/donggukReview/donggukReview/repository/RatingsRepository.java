package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Likes;
import com.donggukReview.donggukReview.entity.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {
    Ratings findByCafeteriaId(Long cafeteriaId);
    boolean existsByCafeteriaId(Long cafeteriaId);
}
