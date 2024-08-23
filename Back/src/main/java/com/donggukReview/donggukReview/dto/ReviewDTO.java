package com.donggukReview.donggukReview.dto;

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
    private Long usersId;
    private Long cafeteriaId;
    private String reviewContents;
    private String reviewRatingsService;
    private String reviewRatingsPrice;
    private String reviewRatingsFlavor;
    private String reviewRatingsTotal;
    private Long reviewRecommended;
}