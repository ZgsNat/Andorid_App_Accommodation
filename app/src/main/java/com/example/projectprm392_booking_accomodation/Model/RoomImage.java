package com.example.projectprm392_booking_accomodation.Model;

public class RoomImage
{
    private int roomImgId;
    private int roomId;
    private String link;

    public RoomImage() {
    }

    public RoomImage(int roomImgId, int roomId, String link) {
        this.roomImgId = roomImgId;
        this.roomId = roomId;
        this.link = link;
    }

    public int getRoomImgId() {
        return roomImgId;
    }

    public void setRoomImgId(int roomImgId) {
        this.roomImgId = roomImgId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
