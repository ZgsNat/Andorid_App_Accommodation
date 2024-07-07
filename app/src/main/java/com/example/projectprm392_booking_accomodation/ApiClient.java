package com.example.projectprm392_booking_accomodation;

import com.example.projectprm392_booking_accomodation.Api.AccommodationApi;
import com.example.projectprm392_booking_accomodation.Api.CommentApi;
import com.example.projectprm392_booking_accomodation.Api.RoomApi;

import com.example.projectprm392_booking_accomodation.Api.IUserApiEndpoint;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String Base_Url = "https://9bfb-42-113-161-193.ngrok-free.app/";
    private AccommodationApi AccommodationApiEnpoint;
    private RoomApi roomApiEnpoint;
    private CommentApi commentApiEnpoint;
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
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        userApiEndpoint = retrofit.create(IUserApiEndpoint.class);
            AccommodationApiEnpoint = retrofit.create(AccommodationApi.class);
            roomApiEnpoint = retrofit.create(RoomApi.class);
        commentApiEnpoint = retrofit.create(CommentApi.class);
    }

    public static AccommodationApi getAccommodationApiEnpoint() {
        return getInstance().AccommodationApiEnpoint;
    }

    public static RoomApi getRoomApiEnpoint() {
        return getInstance().roomApiEnpoint;
    }
    public static CommentApi getCommentApiEnpoint() {return getInstance().commentApiEnpoint;}

    public static IUserApiEndpoint getUserApiEnpoint() {
        return getInstance().userApiEndpoint;
    }
}
