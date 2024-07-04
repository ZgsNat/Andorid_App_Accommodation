package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392_booking_accomodation.DTOs.RegisterDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {

    private static final int REQUEST_CODE_FOR_DOING_SOMETHING = 200;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPasswordConfirm;
    private EditText edtUserName;
    private EditText edtFullName;
    private EditText edtPhone;
    private Button btnRegister;
    private Button btnRegisterToLogin;

    private void bindingView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
        edtUserName = findViewById(R.id.edtUserName);
        edtFullName = findViewById(R.id.edtFullName);
        edtPhone = findViewById(R.id.edtPhone);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegisterToLogin = findViewById(R.id.btnRegisterToLogin);
    }

    private void bindingAction() {
        btnRegister.setOnClickListener(this::onBtnRegisterClick);
        btnRegisterToLogin.setOnClickListener(this::onBtnRegisterToLoginClick);
    }

    private void onBtnRegisterClick(View view) {
        String userEmail = edtEmail.getText().toString();
        String userPass = edtPassword.getText().toString();
        String userPassConfirm = edtPasswordConfirm.getText().toString();
        String userName = edtUserName.getText().toString();
        String fullName = edtFullName.getText().toString();
        String phoneNumber = edtPhone.getText().toString();
        if (userEmail.isEmpty() || userPass.isEmpty() || userPassConfirm.isEmpty()
                || userName.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Ban hay dien day du thong tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(this, "Email sai dinh dang", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!userPass.equals(userPassConfirm)) {
            Toast.makeText(this, "Mat khau khong trung khop", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterDTO dto = new RegisterDTO();
        dto.setUserName(userName);
        dto.setPassword(userPass);
        dto.setEmail(userEmail);
        dto.setName(fullName);
        dto.setPhone(phoneNumber);

        ApiClient.getUserApiEnpoint()
                .registerUser(dto)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Signup.this, "Register successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Signup.this, "CODE: " + response.code(), Toast.LENGTH_SHORT).show();
                            switch (response.code()) {
                                case 409:
                                    Toast.makeText(Signup.this, "User name or email already exists!!!", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(Signup.this, "Register failed", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Signup.this, "Network error", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

    }

    private void onBtnRegisterToLoginClick(View view) {
        Intent i = new Intent(this, Signup.class);
        startActivityForResult(i, REQUEST_CODE_FOR_DOING_SOMETHING);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}