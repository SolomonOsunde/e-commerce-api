package com.example.ecommerce.entity;

public class JwtResponse {

    private UserData userData;
    private String jwtToken;

    public JwtResponse(UserData userData, String jwtToken) {
        this.userData = userData;
        this.jwtToken = jwtToken;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
