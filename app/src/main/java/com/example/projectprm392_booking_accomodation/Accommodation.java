package com.example.projectprm392_booking_accomodation;


public class Accommodation {
    private int accomodationId;
    private String name;
    private double averageStar;
    private String address;
    private String hostName;
    private int image;
    private boolean isFavorite; // New attribute
    private float longitude;
    private float latitude;

    public Accommodation(String name, double avgStar, String address, int imageResource, boolean isFavorite) {
        this.name = name;
        this.averageStar = avgStar;
        this.address = address;
        this.image = imageResource;
        this.isFavorite = isFavorite;
    }

    public Accommodation(float latitude, float longitude, boolean isFavorite, int image, String hostName, String address, double averageStar, String name, int accomodationId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.isFavorite = isFavorite;
        this.image = image;
        this.hostName = hostName;
        this.address = address;
        this.averageStar = averageStar;
        this.name = name;
        this.accomodationId = accomodationId;
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

    public int getAccomodationId() {
        return accomodationId;
    }

    public void setAccomodationId(int accomodationId) {
        this.accomodationId = accomodationId;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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

