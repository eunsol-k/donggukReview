package com.donggukReview.donggukReview.dto.EntityDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingsDTO {
    private Long id;
    private Long cafeteriaId;
    private String ratingsService;
    private String ratingsPrice;
    private String ratingsFlavor;
    private String ratingsTotal;
}