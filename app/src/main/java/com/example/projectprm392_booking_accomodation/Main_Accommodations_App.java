package com.example.projectprm392_booking_accomodation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392_booking_accomodation.Adapter.AccommodationAdapter;
import com.example.projectprm392_booking_accomodation.Model.Accommodation;
import com.example.projectprm392_booking_accomodation.Model.FavoriteAccommodationRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Accommodations_App extends AppCompatActivity {
    private RecyclerView recLoveAccommodation;
    private RecyclerView recAccommodation;
    private AccommodationAdapter adapter1;
    private AccommodationAdapter adapter2;


    private void bindingView(){
        recLoveAccommodation = findViewById(R.id.recLoveAccommodation);
        recAccommodation = findViewById(R.id.recAccommodation);
        recLoveAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void bindingAction(){
        getListAccommodation();
        getListFavorAccommodation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_accommodations_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }


    private void getListAccommodation(){
        recAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            Call<List<Accommodation>> call = ApiClient.getAccommodationApiEnpoint().getListAccommodation();
            call.enqueue(new Callback<List<Accommodation>>() {
                @Override
                public void onResponse(Call<List<Accommodation>> call, Response<List<Accommodation>> response) {
                    Toast.makeText(Main_Accommodations_App.this, "Success", Toast.LENGTH_SHORT).show();
                    List<Accommodation> accommodationList = response.body();
                    for(Accommodation item : accommodationList){
                        item.setFavorite(false);
                    }
                    adapter1 = new AccommodationAdapter(Main_Accommodations_App.this, accommodationList, (accommodation, isFavorite) -> {
                        if (isFavorite) {
                            addToFavorites(accommodation);
                        } else {
                            removeFromFavorites(accommodation);
                        }
                    });
                    recAccommodation.setAdapter(adapter1);

                }

                @Override
                public void onFailure(Call<List<Accommodation>> call, Throwable t) {
                    Toast.makeText(Main_Accommodations_App.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    private void getListFavorAccommodation(){
        recLoveAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Call<List<Accommodation>> call = ApiClient.getAccommodationApiEnpoint().getListFavorAccommodation(1); //UserId
        call.enqueue(new Callback<List<Accommodation>>() {
            @Override
            public void onResponse(Call<List<Accommodation>> call, Response<List<Accommodation>> response) {
                Toast.makeText(Main_Accommodations_App.this, "Success", Toast.LENGTH_SHORT).show();
                List<Accommodation> accommodationList = response.body();
                for(Accommodation item : accommodationList){
                    item.setFavorite(true);
                }
                adapter2 = new AccommodationAdapter(Main_Accommodations_App.this, accommodationList, (accommodation, isFavorite) -> {
                    if (isFavorite) {
                        addToFavorites(accommodation);
                    } else {
                        removeFromFavorites(accommodation);
                    }
                });
                recLoveAccommodation.setAdapter(adapter2);

            }
            @Override
            public void onFailure(Call<List<Accommodation>> call, Throwable t) {
                Toast.makeText(Main_Accommodations_App.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addToFavorites(Accommodation accommodation) {
        Call<Void> call = ApiClient.getAccommodationApiEnpoint().saveFavoriteAccommodation(
                new FavoriteAccommodationRequest(1, accommodation.getAccommodationId())
        );
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    accommodation.setFavorite(true);
                    Toast.makeText(Main_Accommodations_App.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    getListFavorAccommodation(); // Refresh favorite list
                } else {
                    Toast.makeText(Main_Accommodations_App.this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Main_Accommodations_App.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void removeFromFavorites(Accommodation accommodation) {
        Call<Void> call = ApiClient.getAccommodationApiEnpoint().removeFavoriteAccommodation(
                new FavoriteAccommodationRequest(1, accommodation.getAccommodationId())
        );
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    accommodation.setFavorite(false);
                    Toast.makeText(Main_Accommodations_App.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    getListFavorAccommodation(); // Refresh favorite list
                } else {
                    Toast.makeText(Main_Accommodations_App.this, "Failed to remove from favorites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Main_Accommodations_App.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFavorAccommodation();
        getListAccommodation();
    }
}
