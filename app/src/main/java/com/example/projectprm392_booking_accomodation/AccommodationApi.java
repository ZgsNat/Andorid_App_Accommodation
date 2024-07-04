package com.example.projectprm392_booking_accomodation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AccommodationApi
{
    @GET("api/accommodations/get-list-accommodations")
    Call<List<Accommodation>> getListAccommodation();
    @GET("api/accommodations/get-accomodation/{id}")
    Call<Accommodation> getAccommodationById(@Path("id") int id);
}
