package com.init.file_management.dto.request;

import jakarta.validation.constraints.Size;

public class UserCreationRequest {

    private String userName;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
