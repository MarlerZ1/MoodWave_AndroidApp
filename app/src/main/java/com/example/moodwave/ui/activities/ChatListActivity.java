package com.example.moodwave.ui.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodwave.R;
import com.example.moodwave.data.api.ApiService;
import com.example.moodwave.data.api.RetrofitClient;
import com.example.moodwave.data.api.WebSocketClient;
import com.example.moodwave.data.models.Repsonses.ChatResponse;
import com.example.moodwave.ui.adapters.DialogueAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView chatListRecyclerView;
    private WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListRecyclerView = findViewById(R.id.chatListRecyclerView);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getBasicPage();


        webSocketClient = new WebSocketClient(new WebSocketClient.SocketEventListener() {
            @Override
            public void onMessageReceived(String message) {
                runOnUiThread(() -> {
                    System.out.println(message);
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
                    List<ChatResponse> dialogues = gson.fromJson(jsonObject.getAsJsonArray("websocket_message"), new TypeToken<List<ChatResponse>>() {}.getType());
                    DialogueAdapter adapter = new DialogueAdapter(dialogues);
                    chatListRecyclerView.setAdapter(adapter);
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(ChatListActivity.this, "WebSocket Error: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
        String accessToken = getSharedPreferences("appPrefs", MODE_PRIVATE).getString("access_token", null);

        webSocketClient.connect(RetrofitClient.getURL() + "chats/test/", accessToken);
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