package com.example.projectprm392_booking_accomodation.Api;

import com.example.projectprm392_booking_accomodation.Model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentApi {
    @GET("api/comments/get-comments-by-accommodation/{accommodationId}")
    Call<List<Comment>> getCommentsByAccommodationId(@Path("accommodationId") int accommodationId);
    @POST("api/comments/add-comment")
    Call<Void> addComment(@Body Comment comment);
}
