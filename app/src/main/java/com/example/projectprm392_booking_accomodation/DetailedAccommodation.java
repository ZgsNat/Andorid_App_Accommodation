package com.example.projectprm392_booking_accomodation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedAccommodation extends AppCompatActivity {
    private Accommodation accommodation;
    private List<Room> roomList;
    private RecyclerView recRoom;
    private RecyclerView recComment;
    private ImageView imgBackground;
    private TextView txtTitle, txtAvgStar, txtAddress, txtHostName, txtPhoneNumber;
    private void bindingView(){
        recRoom = findViewById(R.id.recListRoom);
        recComment = findViewById(R.id.recListComment);
        imgBackground = findViewById(R.id.imgBackground);
        txtTitle = findViewById(R.id.txtTitle);
        txtAvgStar = findViewById(R.id.txtAvgStar);
        txtAddress = findViewById(R.id.txtAddress);
        txtHostName = findViewById(R.id.txtHostName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
    }
    private void bindingAction(){
        getAccommodation();
        getListRoom();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_accommodation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }

    private void getAccommodation(){
        int accommodationId = getIntent().getIntExtra("AccommodationId", 0);
        Call<Accommodation> call = ApiClient.getAccommodationApiEnpoint().getAccommodationById(accommodationId);
        call.enqueue(new Callback<Accommodation>() {
            @Override
            public void onResponse(Call<Accommodation> call, Response<Accommodation> response) {
                Toast.makeText(DetailedAccommodation.this, "Success", Toast.LENGTH_SHORT).show();
                accommodation = response.body();
                updateUI();
            }

            @Override
            public void onFailure(Call<Accommodation> call, Throwable t) {
                Toast.makeText(DetailedAccommodation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUI() {
        if (accommodation != null) {
            Glide.with(this)
                    .load(accommodation.getImage())
                    .placeholder(R.drawable.baseline_cloud_download) // Ảnh chờ trong khi tải
                    .error(R.drawable.baseline_running_with_errors) // Ảnh lỗi nếu không tải được
                    .into(imgBackground);

            txtTitle.setText(accommodation.getName());
            txtAvgStar.setText(String.valueOf(accommodation.getAverageStar()));
            txtAddress.setText(accommodation.getAddress());
            txtHostName.setText(accommodation.getHostName());
        }
        if(roomList!=null){
            recRoom.setLayoutManager(new GridLayoutManager(this,2));
            RoomAdapter adapter = new RoomAdapter(this, roomList);
            recRoom.setAdapter(adapter);
        }
    }

    private void getListRoom(){
        int accommodationId = getIntent().getIntExtra("AccommodationId", 0);
        Call<List<Room>> call = ApiClient.getRoomApiEnpoint().getListRoom(accommodationId);
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                Toast.makeText(DetailedAccommodation.this, "Success", Toast.LENGTH_SHORT).show();
                roomList = response.body();
                updateUI();
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(DetailedAccommodation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}