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
            return;
        }


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