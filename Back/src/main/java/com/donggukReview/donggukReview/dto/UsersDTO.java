package com.donggukReview.donggukReview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String userId;
    private String userPassword;
    private String userNickname;
    private Long userImageId;
    private String userRole;
}