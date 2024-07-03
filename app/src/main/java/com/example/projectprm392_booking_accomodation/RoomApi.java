package com.example.projectprm392_booking_accomodation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RoomApi {
    @GET("api/Rooms")
    Call<List<Room>> getRooms();
}