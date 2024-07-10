package com.example.projectprm392_booking_accomodation.Api;

import com.example.projectprm392_booking_accomodation.DTOs.ChangePasswordDTO;
import com.example.projectprm392_booking_accomodation.DTOs.LoginDTO;
import com.example.projectprm392_booking_accomodation.DTOs.RegisterDTO;
import com.example.projectprm392_booking_accomodation.DTOs.UserUpdateDTO;
import com.example.projectprm392_booking_accomodation.Model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserApiEndpoint {
    @GET("Users")
    Call<List<User>> getAllUser();

    @GET("Users/{uID}")
    Call<User> getUserById(@Path("uID") int uId);

    @PATCH("Users/{uID}/changepassword")
    Call<ResponseBody> changePassword(@Path("uID") int uId, @Body ChangePasswordDTO dto);

    @PATCH("Users/{uID}")
    Call<ResponseBody> updateUser(@Path("uID") int uId, @Body UserUpdateDTO user);

    @POST("Users/register")
    Call<ResponseBody> registerUser(@Body RegisterDTO registerDto);

    @POST("Users/login")
    Call<ResponseBody> loginUser(@Body LoginDTO loginDto);
}
