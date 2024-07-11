package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.projectprm392_booking_accomodation.DTOs.ChangePasswordDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUserPassword extends AppCompatActivity {

    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;
    private Button btnChangePassword;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private void bindingView() {
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        pref = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = pref.edit();
    }
    private void bindingAction() {
        btnChangePassword.setOnClickListener(this::onBtnChangePasswordClick);
    }

    private void onBtnChangePasswordClick(View view) {
        int userID = Integer.parseInt(pref.getString("userId", ""));
        String newPass = edtNewPassword.getText().toString();
        String confirmPass = edtConfirmPassword.getText().toString();
        String oldPass = edtOldPassword.getText().toString();

        if(newPass.isEmpty() || confirmPass.isEmpty() || oldPass.isEmpty()) {
            Toast.makeText(this, "Bạn phải điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!newPass.equals(confirmPass)) {
            Toast.makeText(this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setOldPassword(oldPass);
        dto.setNewPassword(newPass);

        ApiClient.getUserApiEnpoint()
                .changePassword(userID, dto)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ChangeUserPassword.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ChangeUserPassword.this, UserProfile.class);
                            startActivityForResult(i, 200);
                        }
                        else {
                            switch (response.code()) {
                                case 404:
                                    Toast.makeText(ChangeUserPassword.this, "User not found", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(ChangeUserPassword.this, "Incorrect old password", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(ChangeUserPassword.this, "Change password failed", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ChangeUserPassword.this, "Network error", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_user_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
    }
}