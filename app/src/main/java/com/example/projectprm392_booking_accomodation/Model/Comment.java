package com.example.projectprm392_booking_accomodation.Model;

public class Comment {
    private int commentId;
    private String description;
    private int star;
    private int accommodationId;
    private int userId;
    
    public Comment(int commentId, String description, int star, int accommodationId) {
        this.commentId = commentId;
        this.description = description;
        this.star = star;
        this.accommodationId = accommodationId;
    }

    public Comment(int commentId, String description, int star, int accommodationId, int userId) {
        this.commentId = commentId;
        this.description = description;
        this.star = star;
        this.accommodationId = accommodationId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Comment() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }
}
