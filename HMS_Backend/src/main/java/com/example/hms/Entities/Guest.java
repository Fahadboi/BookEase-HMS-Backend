package com.example.hms.Entities;
import jakarta.persistence.*;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "guestID", unique = true)
    private Long guestID;
    @Column(name = "name", unique = false)
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password", unique = false)
    private String password;
    @Column(name = "phoneNo", unique = false)
    private String phoneNo;
    @Column(name = "address", unique = false)
    private String address;
    @Column(name = "gender", unique = false)
    private String gender;

    public Guest() {

    }

    public void setGuestID(Long guestID) {
        this.guestID = guestID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getGuestID() {
        return guestID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }
}
