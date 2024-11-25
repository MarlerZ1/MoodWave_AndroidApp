package com.example.moodwave;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token;


    public void setToken(String token) {
        this.token = token;
    }

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (token == null || token.isEmpty()) {
            return chain.proceed(originalRequest);
        }

        Request requestWithToken = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token) // Формат заголовка
                .build();

        return chain.proceed(requestWithToken);
    }
}
