package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Signup extends AppCompatActivity {

    private static final int REQUEST_CODE_FOR_DOING_SOMETHING = 200;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPasswordConfirm;
    private Button btnRegister;
    private Button btnRegisterToLogin;

    private void bindingView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
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
        if (userEmail.isEmpty() || userPass.isEmpty() || userPassConfirm.isEmpty()) {
            return;
        }
        if (!userPass.equals(userPassConfirm)) {
            return;
        }


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