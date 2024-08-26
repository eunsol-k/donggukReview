package com.donggukReview.donggukReview.dto.EntityDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long cafeteriaId;
    private String reviewContents;
    private String reviewRatingsService;
    private String reviewRatingsPrice;
    private String reviewRatingsFlavor;
    private String reviewRatingsTotal;
    private Long reviewRecommended;
}