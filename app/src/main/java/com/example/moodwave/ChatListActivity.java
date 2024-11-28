package com.example.moodwave;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodwave.RequestResponse.Repsonses.ChatResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView chatListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListRecyclerView = findViewById(R.id.chatListRecyclerView);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getBasicPage();
    }



    private void getBasicPage(){
        String accessToken = getSharedPreferences("appPrefs", MODE_PRIVATE).getString("access_token", null);

        ApiService apiService = RetrofitClient.getApiService(accessToken);
        Call<List<ChatResponse>> call = apiService.getChatList();

        call.enqueue(new Callback<List<ChatResponse>>() {
            @Override
            public void onResponse(Call<List<ChatResponse>> call, Response<List<ChatResponse>> response) {

                if (response.isSuccessful()) {
                    System.out.println("response.isSuccessful()");
                    System.out.println(response.body().get(0).getName().toString());
                    DialogueAdapter adapter = new DialogueAdapter(response.body());
                    chatListRecyclerView.setAdapter(adapter);
                } else {

//                    try {
//                        System.out.println("There are an error ");
//                        System.out.println("Something went wrong: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        System.out.println("Error reading the error body");
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<ChatResponse>> call, Throwable t) {
                Toast.makeText(ChatListActivity.this, getString(R.string.error_word) + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}