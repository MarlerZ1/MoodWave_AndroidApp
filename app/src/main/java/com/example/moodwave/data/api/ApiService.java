package com.example.moodwave.data.api;

import com.example.moodwave.data.models.Repsonses.ChatResponse;
import com.example.moodwave.data.models.Requests.LoginRequest;
import com.example.moodwave.data.models.Requests.RegistrationRequest;
import com.example.moodwave.data.models.Requests.TokenRefreshRequest;
import com.example.moodwave.data.models.Repsonses.TokenResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("authorization/api/token/")
    Call<TokenResponse> login(@Body LoginRequest loginRequest);

    @POST("authorization/api/registration/")
    Call<ResponseBody> registration(@Body RegistrationRequest registrationRequest);

    @POST("authorization/api/token/refresh/")
    Call<TokenResponse> refreshToken(@Body TokenRefreshRequest tokenRefreshRequest);


    @GET("chats/api/chat_list")
    Call<List<ChatResponse>> getChatList();
}