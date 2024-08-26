package com.donggukReview.donggukReview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CafeteriaDetailResponseDTO {
    Boolean like;
    List<CafeteriaResponseDTO> cafeteriaResponseDTOList;
    List<ReviewResponseDTO> reviewResponseDTOList;
}
