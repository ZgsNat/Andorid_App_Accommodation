package com.example.projectprm392_booking_accomodation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Main_Accommodations_App extends AppCompatActivity {
    private RecyclerView recLoveAccommodation;
    private AccommodationAdapter adapter;
    private List<Accommodation> accommodationList;
    private void bindingView(){
        recLoveAccommodation = findViewById(R.id.recLoveAccommodation);
        recLoveAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        accommodationList = new ArrayList<>();
    }

    private void bindingAction(){
        adapter = new AccommodationAdapter(this, accommodationList);
        recLoveAccommodation.setAdapter(adapter);
    }

    private void createData(){
        accommodationList.add(new Accommodation("Title 1", 4.5, "Address 1", R.drawable.roblox,true));
        accommodationList.add(new Accommodation("Title 2", 4.0, "Address 2", R.drawable.roblox,true));
        accommodationList.add(new Accommodation("Title 3", 5.0, "Address 3", R.drawable.roblox,true));
        accommodationList.add(new Accommodation("Title 4", 3.5, "Address 4", R.drawable.roblox,true));
        accommodationList.add(new Accommodation("Title 5", 4.2, "Address 5", R.drawable.roblox,true));
        accommodationList.add(new Accommodation("Title 6", 3.8, "Address 6", R.drawable.roblox,true));

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
}