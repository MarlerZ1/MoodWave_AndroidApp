package com.example.moodwave.ui.activities;

import android.os.Bundle;
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
import com.example.moodwave.data.models.Repsonses.ChatResponse;
import com.example.moodwave.data.models.Repsonses.MessageResponse;
import com.example.moodwave.ui.adapters.MessageAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView messageListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        messageListRecyclerView = findViewById(R.id.messageListRecyclerView);
        messageListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getInitialMessaged();
    }


    private void getInitialMessaged(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int chat_id = bundle.getInt("chat_id");


            String accessToken = getSharedPreferences("appPrefs", MODE_PRIVATE).getString("access_token", null);

            ApiService apiService = RetrofitClient.getApiService(accessToken);
            Call<List<MessageResponse>> call = apiService.getChat(chat_id);

            call.enqueue(new Callback<List<MessageResponse>>() {
                @Override
                public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                    if (response.isSuccessful()){
                        System.out.println(response.body().get(0).getName());
                        MessageAdapter adapter = new MessageAdapter(response.body());
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