package com.example.moodwave.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodwave.R;
import com.example.moodwave.data.models.Requests.RegistrationRequest;
import com.example.moodwave.data.api.ApiService;
import com.example.moodwave.data.api.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private EditText first_name, last_name, username;
    private EditText email, password1, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        first_name = findViewById(R.id.editTextFirstNameRegistration);
        last_name = findViewById(R.id.editTextLastNameRegistration);
        username = findViewById(R.id.editTextNickNameRegistration);

        email = findViewById(R.id.editTextEmailRegistration);

        password1 = findViewById(R.id.editTextPassword1Registration);
        password2 = findViewById(R.id.editTextPassword2Registration);

        findViewById(R.id.buttonRegistration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });
    }

    private void registration(){
        if (!password1.getText().toString().equals(password2.getText().toString())){
            Toast.makeText(RegistrationActivity.this, getString(R.string.pswd_equals_error), Toast.LENGTH_SHORT).show();
            return;
        }
        RegistrationRequest registrationRequest = new RegistrationRequest(
                first_name.getText().toString(),
                last_name.getText().toString(),
                username.getText().toString(),
                email.getText().toString(),
                password1.getText().toString()
        );

        ApiService apiService = RetrofitClient.getApiService();
        Call<ResponseBody> call = apiService.registration(registrationRequest);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    int statusCode = response.code();

                    if (statusCode == 201){
                        Toast.makeText(RegistrationActivity.this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, getString(R.string.registration_failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, getString(R.string.error_word) + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}