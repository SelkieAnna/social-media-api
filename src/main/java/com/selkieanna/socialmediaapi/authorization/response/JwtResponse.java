package com.selkieanna.socialmediaapi.response;

public class JwtResponse {
    private final String jwtToken;
    private final Long userId;
    private final String username;
    private final String email;

    public JwtResponse(String jwtToken, Long userId, String username, String email) {
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
