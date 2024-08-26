package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoUpdateRequestDTO {
    private String userNickname;
    private String userPrevNickname;
    private String userNextPassword;
}
