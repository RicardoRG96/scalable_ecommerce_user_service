package com.ricardo.scalable.ecommerce.platform.userService.repositories.dto;

import jakarta.validation.constraints.Size;

public class UserUpdatePasswordDto {

    @Size(min = 6, max = 25)
    private String oldPassword;

    @Size(min = 6, max = 25)
    private String newPassword;

    public UserUpdatePasswordDto() {
    }

    public UserUpdatePasswordDto(@Size(min = 6, max = 25) String oldPassword,
            @Size(min = 6, max = 25) String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
