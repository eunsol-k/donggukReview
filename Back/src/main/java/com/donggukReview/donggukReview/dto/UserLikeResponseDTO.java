package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLikeResponseDTO {
    private Long cafeteriaId;
    private String cafeteriaName;
    private String cafeteriaCategory;
    private String cafeteriaImagePath;
}