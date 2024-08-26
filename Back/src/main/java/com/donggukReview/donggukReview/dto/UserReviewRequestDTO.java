package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserReviewRequestDTO {
    private String reviewContents;
    private String reviewRatingsService;
    private String reviewRatingsPrice;
    private String reviewRatingsFlavor;
    private String reviewRatingsTotal;
    private Integer reviewRecommended;
    private Long cafeteriaId;
}