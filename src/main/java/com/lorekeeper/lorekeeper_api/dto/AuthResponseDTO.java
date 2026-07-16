package com.lorekeeper.lorekeeper_api.dto;

public class AuthResponseDTO {

    private String token;
    private UserResponseDTO user;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, UserResponseDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
