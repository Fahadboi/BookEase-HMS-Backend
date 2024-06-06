package com.example.hms.Entities;

import jakarta.persistence.*;

@Entity
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "serviceTypeID", unique = true)
    private Long serviceTypeID;

    @Column(name = "serviceName")
    private String serviceName;

    @Column(name = "servicePrice")
    private double servicePrice;

    @Column(name = "serviceDescription")
    private String serviceDescription;

    public ServiceType() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getServiceTypeID() {
        return serviceTypeID;
    }

    public void setServiceTypeID(Long serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}
