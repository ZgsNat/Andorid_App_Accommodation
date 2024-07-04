package com.example.projectprm392_booking_accomodation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Accommodations_App extends AppCompatActivity {
    private RecyclerView recLoveAccommodation;
    private RecyclerView recAccommodation;
    private AccommodationAdapter adapter;
    private List<Accommodation> accommodationList;



    private void bindingView(){
        recLoveAccommodation = findViewById(R.id.recLoveAccommodation);
        recAccommodation = findViewById(R.id.recAccommodation);
        recLoveAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        accommodationList = new ArrayList<>();
    }

    private void bindingAction(){
        adapter = new AccommodationAdapter(this, accommodationList);
        recLoveAccommodation.setAdapter(adapter);
        getListAccommodation();
    }

    private void createData(){
        accommodationList.add(new Accommodation("Name 1", 4.5, "Address 1", "http://t3.gstatic.com/licensed-image?q=tbn:ANd9GcQtF2ehsVwvBxV5aSgI6IERauqM7kLkXO6XQODmcm6U4YzwuAvM31OwfKw_RgIV6FLc5tIsc7l-tLuJ_jmm97s",true));
        accommodationList.add(new Accommodation("Name 2", 4.0, "Address 2", "http://t3.gstatic.com/licensed-image?q=tbn:ANd9GcQtF2ehsVwvBxV5aSgI6IERauqM7kLkXO6XQODmcm6U4YzwuAvM31OwfKw_RgIV6FLc5tIsc7l-tLuJ_jmm97s",true));
        accommodationList.add(new Accommodation("Name 3", 5.0, "Address 3", "http://t3.gstatic.com/licensed-image?q=tbn:ANd9GcQtF2ehsVwvBxV5aSgI6IERauqM7kLkXO6XQODmcm6U4YzwuAvM31OwfKw_RgIV6FLc5tIsc7l-tLuJ_jmm97s",true));
        accommodationList.add(new Accommodation("Name 4", 3.5, "Address 4", "http://t3.gstatic.com/licensed-image?q=tbn:ANd9GcQtF2ehsVwvBxV5aSgI6IERauqM7kLkXO6XQODmcm6U4YzwuAvM31OwfKw_RgIV6FLc5tIsc7l-tLuJ_jmm97s",true));
        accommodationList.add(new Accommodation("Name 5", 4.2, "Address 5", "http://t3.gstatic.com/licensed-image?q=tbn:ANd9GcQtF2ehsVwvBxV5aSgI6IERauqM7kLkXO6XQODmcm6U4YzwuAvM31OwfKw_RgIV6FLc5tIsc7l-tLuJ_jmm97s",true));
        accommodationList.add(new Accommodation("Name 6", 3.8, "Address 6", "http://t3.gstatic.com/licensed-image?q=tbn:ANd9GcQtF2ehsVwvBxV5aSgI6IERauqM7kLkXO6XQODmcm6U4YzwuAvM31OwfKw_RgIV6FLc5tIsc7l-tLuJ_jmm97s",true));

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
        createData();
        bindingAction();
    }


    private void getListAccommodation(){
        recAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        try {
            Call<List<Accommodation>> call = ApiClient.getAccommodationApiEnpoint().getListAccommodation();
            call.enqueue(new Callback<List<Accommodation>>() {
                @Override
                public void onResponse(Call<List<Accommodation>> call, Response<List<Accommodation>> response) {
                    Toast.makeText(Main_Accommodations_App.this, "Success", Toast.LENGTH_SHORT).show();
                    List<Accommodation> accommodationList = response.body();
                    adapter = new AccommodationAdapter(Main_Accommodations_App.this,accommodationList);
                    recAccommodation.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<List<Accommodation>> call, Throwable t) {
                    Toast.makeText(Main_Accommodations_App.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){

        }
    }
}