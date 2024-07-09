package com.example.projectprm392_booking_accomodation.Model;

public class Owner {
    public int ownerId;
    public String username ;
    public String password;
    public String email ;
    public String name;
    public String avatar ;
    public String phone;

    public Owner(int ownerId, String username, String password, String email, String name, String avatar, String phone) {
        this.ownerId = ownerId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.phone = phone;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
