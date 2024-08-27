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
    private String reviewRatings;
    private Integer reviewRecommended;
}