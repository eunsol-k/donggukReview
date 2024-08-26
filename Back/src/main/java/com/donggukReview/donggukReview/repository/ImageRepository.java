package com.donggukReview.donggukReview.repository;

import com.donggukReview.donggukReview.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByCreatorId(Long creatorId);
    Optional<Image> findByCreatorIdAndIsUserImage(Long creatorId, boolean isUserImage);
}
