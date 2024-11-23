package com.example.moodwave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.moodwave.RequestResponse.Repsonses.TokenResponse;
import com.example.moodwave.RequestResponse.Requests.RegistrationRequest;

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
            Toast.makeText(RegistrationActivity.this, "Пароли не одинаковые", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegistrationActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Регистрация провалилась", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}