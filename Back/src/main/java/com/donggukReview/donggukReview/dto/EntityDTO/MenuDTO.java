package com.donggukReview.donggukReview.dto.EntityDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private Long id;
    private Long cafeteriaId;
    private String menuName;
    private String menuPrice;
}