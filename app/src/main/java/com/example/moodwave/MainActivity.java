package com.example.moodwave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodwave.RequestResponse.Repsonses.TokenResponse;
import com.example.moodwave.RequestResponse.Requests.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.editUsernameAuthorization);
        editPassword = findViewById(R.id.editUsernamePassword);

        findViewById(R.id.buttonAuthorization).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        findViewById(R.id.buttonGoToRegistrationPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void login(){
        System.out.println("It`s alive");
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        LoginRequest loginRequest = new LoginRequest(username, password);


        ApiService apiService = RetrofitClient.getApiService();
        Call<TokenResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {

                    String accessToken = response.body().getAccess();
                    String refreshToken = response.body().getRefresh();

                    // save tocken to local sharedpreferences storage
                    getSharedPreferences("appPrefs", MODE_PRIVATE)
                            .edit()
                            .putString("access_token", accessToken)
                            .putString("refresh_token", refreshToken)
                            .apply();

                    Toast.makeText(MainActivity.this, "Login successed", Toast.LENGTH_SHORT).show();
                    RetrofitClient.updateToken(getSharedPreferences("appPrefs", MODE_PRIVATE).getString("access_token", null));
                    startActivity(new Intent(MainActivity.this, ChatListActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}