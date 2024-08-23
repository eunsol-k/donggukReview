package com.donggukReview.donggukReview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeteriaDTO {
    private Long id;
    private String cafeteriaName;
    private String cafeteriaCategory;
    private String cafeteriaPhone;
    private String cafeteriaAddress;
    private Long cafeteriaImageId;
}