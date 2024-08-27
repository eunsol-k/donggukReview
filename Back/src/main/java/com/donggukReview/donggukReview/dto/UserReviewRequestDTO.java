package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserReviewRequestDTO {
    private String reviewContents;
    private String reviewRatings;
    private Integer reviewRecommended;
    private Long cafeteriaId;
}