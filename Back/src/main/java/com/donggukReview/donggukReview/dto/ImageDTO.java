package com.donggukReview.donggukReview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Long id;
    private Long creatorId;
    private String storedFilePath;
    private Boolean isUserImage;
}