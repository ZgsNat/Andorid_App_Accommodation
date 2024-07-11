package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.net.Uri;
import android.Manifest;
public class DetailedOwnerAccommodation extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private FusedLocationProviderClient fusedLocationClient;

    private Accommodation accommodation;
    private List<Room> roomList;
    private List<Comment> commentList;
    private RecyclerView recRoom;
    private RecyclerView recComment;
    private ImageView imgBackground;
    private TextView txtTitle, txtAvgStar, txtAddress;
    private EditText edtTitle, edtAddress;
    private Button btnSaveimage, btnSavelocation, btnSave;

    private void bindingView(){
        recRoom = findViewById(R.id.recListRoom);
        recComment = findViewById(R.id.recListComment);
        imgBackground = findViewById(R.id.imgBackground);
        edtTitle = findViewById(R.id.edtTitle);
        txtAvgStar = findViewById(R.id.txtAvgStar);
        txtAddress = findViewById(R.id.txtAddress);
        btnSaveimage = findViewById(R.id.btnSaveimage);
        btnSavelocation = findViewById(R.id.btnSavelocation);
        btnSave = findViewById(R.id.btnSave);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void bindingAction(){
        getAccommodation();
        getListRoom();
        getListComments();

        btnSaveimage.setOnClickListener(this::btnSaveimageClick);
        btnSavelocation.setOnClickListener(this::btnSavelocationClick);
        btnSave.setOnClickListener(this::btnSaveClick);
    }

    private void btnSaveClick(View view) {
        if (accommodation != null) {
            // Update the accommodation with any changes from the UI
            String title = edtTitle.getText().toString();
            if (!title.isEmpty()) {
                accommodation.setName(title);
            }

            String address = edtAddress.getText().toString();
            if (!address.isEmpty()) {
                accommodation.setAddress(address);
            }

            // Make the API call to update the accommodation
            Call<Accommodation> call = ApiClient.getAccommodationApiEnpoint().updateAccommodation(accommodation);
            call.enqueue(new Callback<Accommodation>() {
                @Override
                public void onResponse(Call<Accommodation> call, Response<Accommodation> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(DetailedOwnerAccommodation.this, "Accommodation updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailedOwnerAccommodation.this, "Failed to update accommodation", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Accommodation> call, Throwable t) {
                    Toast.makeText(DetailedOwnerAccommodation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(DetailedOwnerAccommodation.this, "Accommodation data is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnSavelocationClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Location")
                .setMessage("Choose how to set the location")
                .setPositiveButton("By Address", (dialog, which) -> promptForAddress())
                .setNegativeButton("By Current Location", (dialog, which) -> getCurrentLocation())
                .show();
    }
    private void promptForAddress() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setTitle("Enter Address")
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> getLocationFromAddress(input.getText().toString()))
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void getLocationFromAddress(String strAddress) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(strAddress, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                // Update the accommodation object or UI with the new coordinates
                accommodation.setLatitude((float) latitude);
                accommodation.setLongitude((float) longitude);
                Toast.makeText(this, "Location set to: " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
                showMapFragment();
            } else {
                Toast.makeText(this, "Unable to find location", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                accommodation.setLatitude((float) latitude);
                accommodation.setLongitude((float) longitude);
                showMapFragment();
                // Update the accommodation object or UI with the new coordinates
                Toast.makeText(this, "Location set to: " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void btnSaveimageClick(View view) {
        openImagePicker();
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            displaySelectedImage();
        }
    }

    private void displaySelectedImage() {
        Glide.with(this)
                .load(imageUri)
                .placeholder(R.drawable.baseline_cloud_download)
                .error(R.drawable.baseline_running_with_errors)
                .into(imgBackground);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_owner_accommodation);
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
                Toast.makeText(DetailedOwnerAccommodation.this, "Success", Toast.LENGTH_SHORT).show();
                accommodation = response.body();
                updateUI();
            }

            @Override
            public void onFailure(Call<Accommodation> call, Throwable t) {
                Toast.makeText(DetailedOwnerAccommodation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getListRoom(){
        int accommodationId = getIntent().getIntExtra("AccommodationId", 0);
        Call<List<Room>> call = ApiClient.getRoomApiEnpoint().getListRoom(accommodationId);
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                Toast.makeText(DetailedOwnerAccommodation.this, "Success", Toast.LENGTH_SHORT).show();
                roomList = response.body();
                updateUI();
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(DetailedOwnerAccommodation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(DetailedOwnerAccommodation.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailedOwnerAccommodation.this, "Failed to get comments", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(DetailedOwnerAccommodation.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void updateUI() {
        if (accommodation != null) {
            Glide.with(this)
                    .load(accommodation.getImage())
                    .placeholder(R.drawable.baseline_cloud_download) // Ảnh chờ trong khi tải
                    .error(R.drawable.baseline_running_with_errors) // Ảnh lỗi nếu không tải được
                    .into(imgBackground);

            edtTitle.setText(accommodation.getName());
            String formattedAverageStar = String.format("%.1f", accommodation.getAverageStar());
            txtAvgStar.setText(formattedAverageStar);
            txtAddress.setText(accommodation.getAddress());
            showMapFragment();
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
}