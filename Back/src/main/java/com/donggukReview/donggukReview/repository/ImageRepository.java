package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByCreatorId(Long userId);
}
