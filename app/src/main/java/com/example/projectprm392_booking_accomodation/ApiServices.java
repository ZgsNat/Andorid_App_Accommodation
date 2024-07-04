package com.example.projectprm392_booking_accomodation;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServices {
    public static final String BASE_URL = "https://9ff5-42-1-77-150.ngrok-free.app/";
    private IUserApiEndpoint userApiEndpoint;
    private static ApiServices instance;

    public static ApiServices getInstance() {
        if (instance == null) {
            instance = new ApiServices();
        }
        return instance;
    }

    private ApiServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApiEndpoint = retrofit.create(IUserApiEndpoint.class);
    }

    public static IUserApiEndpoint getUserApiEnpoint() {
        return getInstance().userApiEndpoint;
    }
}
