package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserLikeListResponseDTO {
    int count;
    List<UserLikeResponseDTO> userLikeList;
}

