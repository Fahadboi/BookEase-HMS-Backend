package com.example.hms.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "billingID" , unique = true)
    private Long billingID;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "bookingID", unique = true)
    private Booking booking;

    @Column(name = "totalDue")
    private double totalDue;

    @Column(name = "billingStatus")
    private String billingStatus;

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    @Column(name = "billingDate")
    private LocalDate billingDate;



    public Billing() {

    }

    public String getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(double totalDue) {
        this.totalDue = totalDue;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Long getBillingID() {
        return billingID;
    }

    public void setBillingID(Long billingID) {
        this.billingID = billingID;
    }



}
