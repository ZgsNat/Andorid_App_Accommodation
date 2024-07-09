package com.example.projectprm392_booking_accomodation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392_booking_accomodation.Adapter.AccommodationAdapter;
import com.example.projectprm392_booking_accomodation.Model.Accommodation;
import com.example.projectprm392_booking_accomodation.Model.FavoriteAccommodationRequest;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Accommodations_App extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recLoveAccommodation;
    private RecyclerView recAccommodation;
    private AccommodationAdapter adapter1;
    private AccommodationAdapter adapter2;

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    private SharedPreferences pref;
    private int userId;

    private void bindingView(){
        pref = getSharedPreferences("user_info", MODE_PRIVATE);
        userId = Integer.parseInt(pref.getString("userId","0"));
        recLoveAccommodation = findViewById(R.id.recLoveAccommodation);
        recAccommodation = findViewById(R.id.recAccommodation);
        recLoveAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void bindingAction(){
        getListAccommodation();
        getListFavorAccommodation();

        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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
        Call<List<Accommodation>> call = ApiClient.getAccommodationApiEnpoint().getListFavorAccommodation(userId); //UserId
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
                new FavoriteAccommodationRequest(userId, accommodation.getAccommodationId())
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
                new FavoriteAccommodationRequest(userId, accommodation.getAccommodationId())
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_profile){
            Toast.makeText(this, "Go to Profile", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, UserProfile.class);
            startActivityForResult(i, 200);
        }
        if(id == R.id.nav_logout) {
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
