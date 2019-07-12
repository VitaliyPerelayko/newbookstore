package com.intexsoft.web.dto.response;

public class TokenDTO {
    private String token;
    private String type = "Bearer";

    public TokenDTO(String accessToken) {
        this.token = accessToken;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}

