package com.example.projectprm392_booking_accomodation;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUserApiEndpoint {
    @GET("Users")
    Call<List<User>> getAllUser();

    @GET("Users/{uID}")
    Call<User> getUserById(@Path("uID") int uId);

    @PUT("Users/{uID}")
    Call<User> updateUser(@Path("uID") int uId, @Body User user);

    @POST("Users/register")
    Call<ResponseBody> registerUser(@Body RegisterDTO registerDto);

    @POST("Users/login")
    Call<ResponseBody> loginUser(@Body LoginDTO loginDto);
}
