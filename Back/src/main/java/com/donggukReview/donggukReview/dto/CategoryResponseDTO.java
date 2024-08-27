package com.donggukReview.donggukReview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryResponseDTO {
    List<String> category;
}
