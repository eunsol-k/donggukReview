package com.donggukReview.donggukReview.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WithdrawRequestDTO {
    private String userPassword;
    private String userPasswordConfirm;

    public boolean checkPassword() {
        return this.userPassword.equals(this.userPasswordConfirm);
    }
}
