package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectprm392_booking_accomodation.DTOs.UserUpdateDTO;
import com.example.projectprm392_booking_accomodation.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {

    private TextView tvUserName1;
    private TextView tvUserEmail1;
    private TextView tvChangePass;
    private EditText tvUserPhone;
    private EditText tvUserEmail;
    private EditText tvUserName;
    private Button btnEditProfile;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private void bindingView() {
        tvUserName1 = findViewById(R.id.tvUserName1);
        tvUserEmail1 = findViewById(R.id.tvUserEmail1);
        tvUserPhone = findViewById(R.id.tvUserPhone);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserName = findViewById(R.id.tvUserName);
        tvChangePass = findViewById(R.id.tvChangePass);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        pref = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = pref.edit();
    }
    private void bindingAction() {
        btnEditProfile.setOnClickListener(this::onBtnEditProfileClick);
        tvChangePass.setOnClickListener(this::onTvChangePassClick);
    }

    private void onTvChangePassClick(View view) {
        Intent i = new Intent(this, ChangeUserPassword.class);
        startActivityForResult(i, 200);
    }

    private void onBtnEditProfileClick(View view) {
        int userID = Integer.parseInt(pref.getString("userId", ""));
        String userName = tvUserName.getText().toString();
        String userEmail = tvUserEmail.getText().toString();
        String userPhone = tvUserPhone.getText().toString();
        if (userName.isEmpty() || userEmail.isEmpty() || userPhone.isEmpty()) {
            Toast.makeText(this, "Ban hay dien day du thong tin", Toast.LENGTH_SHORT).show();
            return;
        }

        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setUserName(userName);
        dto.setEmail(userEmail);
        dto.setPhone(userPhone);

        ApiClient.getUserApiEnpoint()
                .updateUser(userID, dto)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(UserProfile.this, "CODE: " + response.code(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(UserProfile.this, "Update successfully", Toast.LENGTH_SHORT).show();
                            // Reload activity
                            Intent intent = new Intent(UserProfile.this, UserProfile.class);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(UserProfile.this, "CODE: " + response.code(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(UserProfile.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(UserProfile.this, "Network error", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        onInit();
    }

    private void onInit() {
        String userName = pref.getString("username", "");
        String userEmail = pref.getString("email", "");
        String userPhone = pref.getString("phone", "");

        tvUserName1.setText(userName);
        tvUserEmail1.setText(userEmail);
        tvUserPhone.setText(userPhone);
        tvUserEmail.setText(userEmail);
        tvUserName.setText(userName);
    }
}