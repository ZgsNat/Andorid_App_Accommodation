package com.example.projectprm392_booking_accomodation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectprm392_booking_accomodation.Adapter.CommentAdapter;
import com.example.projectprm392_booking_accomodation.Adapter.RoomAdapter;
import com.example.projectprm392_booking_accomodation.Fragments.MapsFragment;
import com.example.projectprm392_booking_accomodation.Model.Accommodation;
import com.example.projectprm392_booking_accomodation.Model.Comment;
import com.example.projectprm392_booking_accomodation.Model.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedAccommodation extends AppCompatActivity {
    private Accommodation accommodation;
    private List<Room> roomList;
    private List<Comment> commentList;
    private RecyclerView recRoom;
    private RecyclerView recComment;
    private ImageView imgBackground;
    private TextView txtTitle, txtAvgStar, txtAddress, txtHostName, txtPhoneNumber;
    private EditText editTextComment;
    private RatingBar ratingBarComment;
    private Button buttonSubmitComment;

    private void bindingView(){
        recRoom = findViewById(R.id.recListRoom);
        recComment = findViewById(R.id.recListComment);
        imgBackground = findViewById(R.id.imgBackground);
        txtTitle = findViewById(R.id.txtTitle);
        txtAvgStar = findViewById(R.id.txtAvgStar);
        txtAddress = findViewById(R.id.txtAddress);
        txtHostName = findViewById(R.id.txtHostName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        editTextComment = findViewById(R.id.edtComment);
        ratingBarComment = findViewById(R.id.ratingBarComment);
        buttonSubmitComment = findViewById(R.id.buttonSubmitComment);
    }
    private void bindingAction(){
        getAccommodation();
        getListRoom();
        getListComments();
        showMapFragment();
        buttonSubmitComment.setOnClickListener(this::btnSubmitCommentClick);
    }

    private void btnSubmitCommentClick(View view) {
        String commentText = editTextComment.getText().toString();
        int accommodationId = getIntent().getIntExtra("AccommodationId", 0);
        float stars = ratingBarComment.getRating();
        if (commentText.isEmpty()) {
            Toast.makeText(DetailedAccommodation.this, "Please enter a comment.", Toast.LENGTH_SHORT).show();
            return;
        }
        Comment comment = new Comment();
        comment.setDescription(commentText);
        comment.setStar((int) stars);
        comment.setAccommodationId(accommodationId);
        comment.setUserId(1); //UserId
        Call<Void> call = ApiClient.getCommentApiEnpoint().addComment(comment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DetailedAccommodation.this, "Comment added successfully!", Toast.LENGTH_SHORT).show();
                    editTextComment.setText("");
                    ratingBarComment.setRating(0);
                    getListComments();
                } else {
                    Toast.makeText(DetailedAccommodation.this, "Failed to add comment.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DetailedAccommodation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
            String formattedAverageStar = String.format("%.1f", accommodation.getAverageStar());
            txtAvgStar.setText(formattedAverageStar);
            txtAddress.setText(accommodation.getAddress());
            txtHostName.setText(accommodation.getHostName());
        }
        if(roomList!=null){
            recRoom.setLayoutManager(new GridLayoutManager(this,2));
            RoomAdapter adapter = new RoomAdapter(this, roomList);
            recRoom.setAdapter(adapter);
        }
        if (commentList != null) {
            recComment.setLayoutManager(new GridLayoutManager(this, 1));
            CommentAdapter commentAdapter = new CommentAdapter(this, commentList);
            recComment.setAdapter(commentAdapter);
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
    private void getListComments() {
        int accommodationId = getIntent().getIntExtra("AccommodationId", 0);
        Call<List<Comment>> call = ApiClient.getCommentApiEnpoint().getCommentsByAccommodationId(accommodationId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentList = response.body();
                    updateUI();
                    Toast.makeText(DetailedAccommodation.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailedAccommodation.this, "Failed to get comments", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(DetailedAccommodation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showMapFragment() {
        MapsFragment mapsFragment = new MapsFragment();
        Bundle args = new Bundle();
        if (accommodation != null) {
            args.putDouble("ACCOMMODATION_LATITUDE", accommodation.getLatitude());
            args.putDouble("ACCOMMODATION_LONGITUDE", accommodation.getLongitude());
        }
        args.putDouble("DESTINATION_LATITUDE", 21.012416924859732); // Tọa độ Đại học FPT
        args.putDouble("DESTINATION_LONGITUDE", 105.5252888546371); // Tọa độ Đại học FPT
        mapsFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, mapsFragment);
        transaction.commit();
    }
}