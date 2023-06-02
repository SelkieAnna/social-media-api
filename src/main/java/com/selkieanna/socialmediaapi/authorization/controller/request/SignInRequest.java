package com.selkieanna.socialmediaapi.authorization.controller.request;

public class SignInRequest {
    private String userName;
    private String password;

    public SignInRequest(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
