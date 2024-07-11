package com.example.projectprm392_booking_accomodation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm392_booking_accomodation.Adapter.AccommodationAdapter;
import com.example.projectprm392_booking_accomodation.Adapter.OwnerAccommodationAdapter;
import com.example.projectprm392_booking_accomodation.Model.Accommodation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerCRUDAccommodationMain extends AppCompatActivity {
    private RecyclerView recAccommodation;
    private RecyclerView recOwnerAccommodation;
    private AccommodationAdapter adapter1;
    private OwnerAccommodationAdapter adapter2;
    private SharedPreferences pref;
    private int OwnerId;
    //Search
    private AutoCompleteTextView edtAccommodationName;
    private List<String> ListAccommodationName = new ArrayList<>();
    private List<Accommodation> allAccommodations = new ArrayList<>();
    private ArrayAdapter<String> searchAdapter;

    //

    private void BindingView(){
        pref = getSharedPreferences("user_info", MODE_PRIVATE);
        OwnerId = Integer.parseInt(pref.getString("ownerId","1"));
        recAccommodation = findViewById(R.id.recAccommodation);
        recOwnerAccommodation = findViewById(R.id.recOwnerAccommodation);


        edtAccommodationName = findViewById(R.id.txtSearch);
        searchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, ListAccommodationName);
        edtAccommodationName.setAdapter(searchAdapter);
    }
    private void bindingAction(){
        getListAccommodation();
        getListOwnerAccommodation();
        setupSearchFilter();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_crudaccommodation_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BindingView();
        bindingAction();
    }
    private void getListAccommodation(){
        recAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Call<List<Accommodation>> call = ApiClient.getAccommodationApiEnpoint().getListAccommodation();

        call.enqueue(new Callback<List<Accommodation>>() {
            @Override
            public void onResponse(Call<List<Accommodation>> call, Response<List<Accommodation>> response) {
                Toast.makeText(OwnerCRUDAccommodationMain.this, "Success", Toast.LENGTH_SHORT).show();
                List<Accommodation> accommodationList = response.body();
                for(Accommodation item : accommodationList){
                    item.setFavorite(false);
                }
                adapter1 = new AccommodationAdapter(OwnerCRUDAccommodationMain.this, accommodationList, (accommodation, isFavorite) -> {
                    if (isFavorite) {
                        //addToFavorites(accommodation);
                    } else {
                        //removeFromFavorites(accommodation);
                    }
                });
                allAccommodations = accommodationList;
                recAccommodation.setAdapter(adapter1);

            }

            @Override
            public void onFailure(Call<List<Accommodation>> call, Throwable t) {
                Toast.makeText(OwnerCRUDAccommodationMain.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getListOwnerAccommodation(){
        recOwnerAccommodation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Call<List<Accommodation>> call = ApiClient.getAccommodationApiEnpoint().getAccommodationByOwnerId(OwnerId);
        call.enqueue(new Callback<List<Accommodation>>() {
            @Override
            public void onResponse(Call<List<Accommodation>> call, Response<List<Accommodation>> response) {
                Toast.makeText(OwnerCRUDAccommodationMain.this, "Success", Toast.LENGTH_SHORT).show();
                List<Accommodation> accommodationList = response.body();
                for(Accommodation item : accommodationList){
                    item.setFavorite(false);
                }
                adapter2 = new OwnerAccommodationAdapter(OwnerCRUDAccommodationMain.this, accommodationList, (accommodation, isFavorite) -> {
                    if (isFavorite) {
                        //addToFavorites(accommodation);
                    } else {
                        //removeFromFavorites(accommodation);
                    }
                });
                recOwnerAccommodation.setAdapter(adapter2);

            }

            @Override
            public void onFailure(Call<List<Accommodation>> call, Throwable t) {
                Toast.makeText(OwnerCRUDAccommodationMain.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupSearchFilter() {
        edtAccommodationName.setThreshold(1); // Bắt đầu gợi ý sau 1 ký tự
        edtAccommodationName.setOnItemClickListener((parent, view, position, id) -> {
            String selectedAccommodation = (String) parent.getItemAtPosition(position);
            // Lọc danh sách chỗ ở và cập nhật RecyclerView
            filterAccommodations(selectedAccommodation);
        });
        edtAccommodationName.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filterAccommodations(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAccommodations(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (s.toString().isEmpty()) {
                    getListAccommodation();
                }
                filterAccommodations(s.toString());
            }
        });
    }

    private void filterAccommodations(String query) {
        List<Accommodation> filteredList = new ArrayList<>();
        for (Accommodation accommodation : allAccommodations) {
            if (accommodation.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(accommodation);
            }
        }
        adapter1.updateData(filteredList); // Cập nhật adapter với danh sách đã lọc
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListAccommodation();
    }
}