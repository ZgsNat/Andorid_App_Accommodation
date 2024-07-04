package com.example.projectprm392_booking_accomodation;

import com.example.projectprm392_booking_accomodation.Api.AccommodationApi;
import com.example.projectprm392_booking_accomodation.Api.RoomApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String Base_Url = "https://9af6-42-113-161-193.ngrok-free.app/";
    private AccommodationApi AccommodationApiEnpoint;
    private RoomApi roomApiEnpoint;
    private static ApiClient instance;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    private ApiClient() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            AccommodationApiEnpoint = retrofit.create(AccommodationApi.class);
            roomApiEnpoint = retrofit.create(RoomApi.class);
    }

    public static AccommodationApi getAccommodationApiEnpoint() {
        return getInstance().AccommodationApiEnpoint;
    }

    public static RoomApi getRoomApiEnpoint() {
        return getInstance().roomApiEnpoint;
    }
}
