package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserReviewListResponseDTO {
    int count;
    List<UserReviewResponseDTO> userReviewList;
}