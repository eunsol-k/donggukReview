package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO {
    private String userId;
    private String userNickname;
    private String userPassword;
}
