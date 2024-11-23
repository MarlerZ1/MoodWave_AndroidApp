package com.example.moodwave;

import com.example.moodwave.RequestResponse.Requests.LoginRequest;
import com.example.moodwave.RequestResponse.Requests.TokenRefreshRequest;
import com.example.moodwave.RequestResponse.Repsonses.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // Эндпоинт для получения токена
    @POST("authorization/api/token/")
    Call<TokenResponse> login(@Body LoginRequest loginRequest);

    // Эндпоинт для обновления токена
    @POST("authorization/api/token/refresh/")
    Call<TokenResponse> refreshToken(@Body TokenRefreshRequest tokenRefreshRequest);
}