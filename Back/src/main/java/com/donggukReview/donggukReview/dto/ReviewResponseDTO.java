package com.donggukReview.donggukReview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewResponseDTO {
    private String reviewContents;
    private String reviewRatings;
    private Integer reviewRecommended;
}
