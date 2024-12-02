package com.example.moodwave.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodwave.R;
import com.example.moodwave.data.api.ApiService;
import com.example.moodwave.data.api.RetrofitClient;
import com.example.moodwave.data.api.WebSocketClient;
import com.example.moodwave.data.models.Repsonses.ChatResponse;
import com.example.moodwave.data.models.Repsonses.MessageResponse;
import com.example.moodwave.data.models.Requests.DeleteMessageRequest;
import com.example.moodwave.data.models.Requests.SendMessageFormRequest;
import com.example.moodwave.data.models.Requests.SendMessageRequest;
import com.example.moodwave.ui.adapters.MessageAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView messageListRecyclerView;
    private WebSocketClient webSocketClient;

    private EditText newMessageEditText;
    private Button sendBtn;

    private int chatId;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        newMessageEditText = findViewById(R.id.newMessageEditText);
        sendBtn = findViewById(R.id.sendBtn);
        messageListRecyclerView = findViewById(R.id.messageListRecyclerView);
        messageListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        webSocketClient = new WebSocketClient(new WebSocketClient.SocketEventListener() {
            @Override
            public void onMessageReceived(String message) {
                runOnUiThread(() -> {

                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
                    MessageResponse messageResponse = gson.fromJson(jsonObject.getAsJsonObject("websocket_message"), MessageResponse.class);
                    switch (messageResponse.getMessage_type() ){
                        case "new_message":
                            adapter.addItem(messageResponse);
                            break;
                        case "delete_message":
                            adapter.deleteItem(messageResponse.getMessage_id());
                            break;
                    }

                });
            }

            @Override
            public void onError(String error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                SendMessageRequest sendMessageRequest = new SendMessageRequest(new SendMessageFormRequest(newMessageEditText.getText().toString()), chatId);
                webSocketClient.sendMessage(gson.toJson(sendMessageRequest));
            }
        });


        String accessToken = getSharedPreferences("appPrefs", MODE_PRIVATE).getString("access_token", null);

        webSocketClient.connect(RetrofitClient.getURL() + "chats/messages/add_new_message", accessToken);
        getInitialMessaged();
    }


    private void getInitialMessaged(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            chatId = bundle.getInt("chat_id");



            String accessToken = getSharedPreferences("appPrefs", MODE_PRIVATE).getString("access_token", null);

            ApiService apiService = RetrofitClient.getApiService(accessToken);
            Call<List<MessageResponse>> call = apiService.getChat(chatId);

            call.enqueue(new Callback<List<MessageResponse>>() {
                @Override
                public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                    if (response.isSuccessful()){
                        adapter = new MessageAdapter(response.body(), new MessageAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int message_id) {
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                webSocketClient.sendMessage(gson.toJson(new DeleteMessageRequest(message_id)));

                            }
                        });
                        messageListRecyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                    Toast.makeText(ChatActivity.this, getString(R.string.error_word) + t.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println(t.getMessage());
                }
            });
        } else
            throw new IllegalArgumentException("Bundle must be initialize");




    }
}