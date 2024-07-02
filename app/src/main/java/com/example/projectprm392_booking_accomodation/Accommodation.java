package com.example.projectprm392_booking_accomodation;


public class Accommodation {
    private String name;
    private double avgStar;
    private String address;
    private int imageResource;
    private boolean isFavorite; // New attribute

    public Accommodation(String name, double avgStar, String address, int imageResource, boolean isFavorite) {
        this.name = name;
        this.avgStar = avgStar;
        this.address = address;
        this.imageResource = imageResource;
        this.isFavorite = isFavorite;
    }

    public String getTitle() {
        return name;
    }

    public double getAvgStar() {
        return avgStar;
    }

    public String getAddress() {
        return address;
    }

    public int getImageResource() {
        return imageResource;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}

