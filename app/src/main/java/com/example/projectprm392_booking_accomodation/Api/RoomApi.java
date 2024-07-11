package com.example.projectprm392_booking_accomodation.Api;

import com.example.projectprm392_booking_accomodation.Model.Room;
import com.example.projectprm392_booking_accomodation.Model.RoomImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RoomApi {
    @GET("api/rooms/get-room-accommodation")
    Call<List<Room>> getListRoom(@Query("accommodationId") int accommodationId);
    @GET("api/rooms/get-room-images")
    Call<List<RoomImage>> GetListRoomImage(@Query("roomId") int roomId);
}