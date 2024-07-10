package com.example.projectprm392_booking_accomodation.Model;

public class FavoriteAccommodationRequest {
    private int userId;
    private int accommodationId;

    public FavoriteAccommodationRequest() {
    }

    public FavoriteAccommodationRequest(int userId, int accommodationId) {
        this.userId = userId;
        this.accommodationId = accommodationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }
}
