package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private final int REQUEST_CODE_FOR_DOING_SOMETHING = 200;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnLoginToRegister;

    private void bindingView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginToRegister = findViewById(R.id.btnLoginToRegister);
    }

    private void bindingAction() {
        btnLogin.setOnClickListener(this::onBtnLoginClick);
        btnLoginToRegister.setOnClickListener(this::onBtnLoginToRegisterClick);
    }

    private void onBtnLoginClick(View view) {
        String userEmail = edtEmail.getText().toString();
        String userPass = edtPassword.getText().toString();
        if (userEmail.isEmpty() || userPass.isEmpty()) {
            Toast.makeText(this, "Ban hay dien tai khoan va mat khau", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginDTO dto = new LoginDTO();
        dto.setUserName(userEmail);
        dto.setPassword(userPass);

        ApiServices.getUserApiEnpoint()
                .loginUser(dto)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "CODE: " + response.code(), Toast.LENGTH_SHORT).show();
                            switch (response.code()) {
                                case 404:
                                    Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Login.this, "Network error", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

    }

    private void onBtnLoginToRegisterClick(View view) {
        Intent i = new Intent(this, Signup.class);
        startActivityForResult(i, REQUEST_CODE_FOR_DOING_SOMETHING);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
    }
}