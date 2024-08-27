package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoResponseDTO {
    private String userId;
    private String userName;
    private String userImagePath;
    private int likeNum;
    private int reviewNum;
}
