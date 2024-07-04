package com.example.projectprm392_booking_accomodation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RoomApi {
    @GET("api/rooms/get-room-accommodation")
    Call<List<Room>> getListRoom(@Query("accommodationId") int accommodationId);
}