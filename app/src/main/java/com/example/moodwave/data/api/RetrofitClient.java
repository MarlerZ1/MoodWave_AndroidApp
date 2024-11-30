package com.example.moodwave.data.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/";
    private static Retrofit retrofit;
    private static AuthInterceptor authInterceptor;
    public static ApiService getApiService(){
        return getApiService("");
    }

    public static ApiService getApiService(String token) {
        if (retrofit == null) {
            authInterceptor = new AuthInterceptor(token);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

    public static void updateToken(String newToken) {
        if (authInterceptor != null) {
            authInterceptor.setToken(newToken);
        }
    }
    public static String getURL(){
        return BASE_URL;
    }
}
