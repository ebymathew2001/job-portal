package com.jobportal.Job_Portal.authentication.dto;

public class AuthenticationResponse {
    private String token;
    private String tokenType="Bearer";
    private String role;

    public AuthenticationResponse(){}

    public AuthenticationResponse(String token, String role){
        this.token = token;
        this.role=role;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
