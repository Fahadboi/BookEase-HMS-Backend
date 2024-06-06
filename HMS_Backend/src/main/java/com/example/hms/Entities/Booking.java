package com.example.hms.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingID")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomID")  // Changed to "roomID"
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guestId", referencedColumnName = "guestID")  // Changed to "guestID"
    private Guest guest;

    @Column(name = "checkIn")
    private LocalDate checkIn;

    @Column(name = "checkOut")
    private LocalDate checkOut;

    @Column(name = "totalDays")
    private int totalDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "bookingStatus")
    private BookingStatus bookingStatus;

    // Constructors
    public Booking() {}



    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }
    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }


}

