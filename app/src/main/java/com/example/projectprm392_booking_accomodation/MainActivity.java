package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_FOR_DOING_SOMETHING = 200;
    private Button btnGoToLoginPage;
    private Button btnGoToRegisterPage;

    private void bindingView() {
        btnGoToLoginPage = findViewById(R.id.btnLoginPage);
        btnGoToRegisterPage = findViewById(R.id.btnSignupPage);
    }

    private void bindingAction() {
        btnGoToLoginPage.setOnClickListener(this::onBtnGoToLoginPageClick);
        btnGoToRegisterPage.setOnClickListener(this::onBtnGoToRegisterPageClick);
    }

    private void onBtnGoToRegisterPageClick(View view) {
        Intent i = new Intent(this, Signup.class);
        startActivityForResult(i, REQUEST_CODE_FOR_DOING_SOMETHING);
    }


    private void onBtnGoToLoginPageClick(View view) {
        Intent i = new Intent(this, Login.class);
        startActivityForResult(i, REQUEST_CODE_FOR_DOING_SOMETHING);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
    }
}