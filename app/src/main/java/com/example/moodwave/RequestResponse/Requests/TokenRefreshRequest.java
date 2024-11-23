package com.example.moodwave.RequestResponse.Requests;

public class TokenRefreshRequest {
    private String refresh;

    public TokenRefreshRequest(String refresh) {
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }
}