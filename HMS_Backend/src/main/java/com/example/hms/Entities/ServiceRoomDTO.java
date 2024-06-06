package com.example.hms.Entities;

import java.time.LocalDate;

public class ServiceRoomDTO {
    private Long bookingId;
    private Long serviceTypeId;
    private LocalDate serviceRoomDate;
    private String serviceRoomStatus;

    // Default constructor sets the current date
    public ServiceRoomDTO() {
        this.serviceRoomDate = LocalDate.now(); // Set current date at the time of DTO creation
    }

    // Getters and setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public LocalDate getServiceRoomDate() {
        return serviceRoomDate;
    }

    public String getServiceRoomStatus() {
        return serviceRoomStatus;
    }

    public void setServiceRoomStatus(String serviceRoomStatus) {
        this.serviceRoomStatus = serviceRoomStatus;
    }
}
