package com.example.hms.Entities;

import com.example.hms.Entities.Booking;
import com.example.hms.Entities.ServiceType;
import com.example.hms.Entities.Staff;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ServiceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "serviceRoomId" , unique = true)
    private Long serviceRoomId;

    @ManyToOne
    @JoinColumn(name = "bookingId", referencedColumnName = "bookingID")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "serviceTypeId", referencedColumnName = "serviceTypeID")
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "staffId", referencedColumnName = "staffID")
    private Staff staff;

    @Column(name = "serviceRoomDate")
    private LocalDate serviceRoomDate;

    @Column(name = "serviceRoomStatus")
    private String serviceRoomStatus;

    public ServiceRoom() {
    }

    public Long getServiceRoomId() {
        return serviceRoomId;
    }

    public void setServiceRoomId(Long serviceRoomId) {
        this.serviceRoomId = serviceRoomId;
    }

    public String getServiceRoomStatus() {
        return serviceRoomStatus;
    }

    public void setServiceRoomStatus(String serviceRoomStatus) {
        this.serviceRoomStatus = serviceRoomStatus;
    }

    public LocalDate getServiceRoomDate() {
        return serviceRoomDate;
    }

    public void setServiceRoomDate(LocalDate serviceRoomDate) {
        this.serviceRoomDate = serviceRoomDate;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


}
