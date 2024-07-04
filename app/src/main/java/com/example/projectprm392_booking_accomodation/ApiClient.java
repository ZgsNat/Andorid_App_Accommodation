package com.example.projectprm392_booking_accomodation;


import com.example.projectprm392_booking_accomodation.Api.IUserApiEndpoint;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://9ff5-42-1-77-150.ngrok-free.app/";
    private IUserApiEndpoint userApiEndpoint;
    private static ApiClient instance;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    private ApiClient() {
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
