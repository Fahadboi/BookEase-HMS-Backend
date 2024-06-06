package com.example.hms.Entities;

import jakarta.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "roomID", unique = true)
    private Long roomID;
    @Column(name = "roomNo", unique = true)
    private int roomNo;
    @Column(name = "roomType")
    private String roomType;
    @Column(name = "roomStatus")
    private String roomStatus; // Can be "Available" or "Occupied"
    @Column(name = "roomDescription")
    private String roomDescription;

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Column(name = "pricePerNight")
    private double pricePerNight;


    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }


    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
        if (roomType.equalsIgnoreCase("Standard")) {
            setPricePerNight(2000);
        } else if (roomType.equalsIgnoreCase("Luxury")) {
            setPricePerNight(4000);
        }
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    // Default constructor
    public Room() {
    }


}
