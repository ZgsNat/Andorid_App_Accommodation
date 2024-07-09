package com.example.projectprm392_booking_accomodation.Api;

import com.example.projectprm392_booking_accomodation.Model.Accommodation;
import com.example.projectprm392_booking_accomodation.Model.FavoriteAccommodationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccommodationApi
{
    @GET("api/accommodations/get-list-accommodations")
    Call<List<Accommodation>> getListAccommodation();

    @GET("api/accommodations/get-fav-list-accommodations")
    Call<List<Accommodation>> getListFavorAccommodation(@Query("userId") int userId);

    @GET("api/accommodations/get-accomodation/{id}")
    Call<Accommodation> getAccommodationById(@Path("id") int id);

    @GET("api/accommodations/get-accomodation-by-owner/{id}")
    Call<List<Accommodation>> getAccommodationByOwnerId(@Path("id") int ownerId);

    @POST("api/accommodations/save-fav-accommodation")
    Call<Void> saveFavoriteAccommodation(@Body FavoriteAccommodationRequest request);

    @HTTP(method = "DELETE", path = "api/accommodations/remove-fav-accommodation", hasBody = true)
    Call<Void> removeFavoriteAccommodation(@Body FavoriteAccommodationRequest request);

    @PUT("api/accommodations/update-accommodation")
    Call<Accommodation> updateAccommodation(@Body Accommodation accommodation);
}
