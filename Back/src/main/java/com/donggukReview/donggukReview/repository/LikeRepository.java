package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByUserId(Long userId);
    boolean existsByUserIdAndCafeteriaId(Long userId, Long cafeteriaId);
    Likes findByUserIdAndCafeteriaId(Long userId, Long cafeteriaId);
}
