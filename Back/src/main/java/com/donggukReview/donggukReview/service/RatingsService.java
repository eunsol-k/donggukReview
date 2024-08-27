package com.donggukReview.donggukReview.service;

import com.donggukReview.donggukReview.dto.EntityDTO.RatingsDTO;
import com.donggukReview.donggukReview.entity.Ratings;
import com.donggukReview.donggukReview.repository.RatingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingsService {
    private final RatingsRepository ratingsRepository;
    public RatingsDTO getRatingsById(Long cafeteriaId) {
        Ratings ratings = ratingsRepository.findByCafeteriaId(cafeteriaId);

        RatingsDTO ratingsDTO = new RatingsDTO();
        ratingsDTO.setId(ratings.getId());
        ratingsDTO.setRatings(ratings.getRatings());
        ratingsDTO.setCafeteriaId(ratings.getCafeteriaId());

        return ratingsDTO;
    }
}
