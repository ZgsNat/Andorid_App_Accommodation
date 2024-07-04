package com.example.projectprm392_booking_accomodation;


public class Accommodation {
    private int accommodationId;
    private String name;
    private double averageStar;
    private String address;
    private String hostName;
    private String image; // Đổi kiểu dữ liệu từ int sang String
    private boolean isFavorite;
    private float longitude;
    private float latitude;

    public Accommodation(String name, double avgStar, String address, String imageUrl, boolean isFavorite) {
        this.name = name;
        this.averageStar = avgStar;
        this.address = address;
        this.image = imageUrl; // Đổi kiểu dữ liệu từ int sang String
        this.isFavorite = isFavorite;
    }

    public Accommodation(int accommodationId, String name, double averageStar, String address, String hostName, String image, boolean isFavorite, float longitude, float latitude) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.averageStar = averageStar;
        this.address = address;
        this.hostName = hostName;
        this.image = image;
        this.isFavorite = isFavorite;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAverageStar() {
        return averageStar;
    }

    public void setAverageStar(double averageStar) {
        this.averageStar = averageStar;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getTitle() {
        return name;
    }

    public double getAvgStar() {
        return averageStar;
    }

    public String getAddress() {
        return address;
    }


    public boolean isFavorite() {
        return isFavorite;
    }
}

