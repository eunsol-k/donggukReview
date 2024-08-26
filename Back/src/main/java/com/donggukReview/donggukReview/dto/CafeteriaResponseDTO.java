package com.donggukReview.donggukReview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CafeteriaResponseDTO {
    private Long cafeteriaId;
    private String cafeteriaName;
    private String cafeteriaCategory;
    private String storedFilePath;
}
