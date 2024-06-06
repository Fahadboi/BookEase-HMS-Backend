package com.example.hms.Entities;
import jakarta.persistence.*;

@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "staffID", unique = true)
    private Long staffID;
    @Column(name = "name", unique = false)
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password", unique = false)
    private String password;
    @Column(name = "role", unique = false)
    private String role;
    @Column(name = "phoneNo", unique = false)
    private String phoneNo;
    @Column(name = "address", unique = false)
    private String address;
    @Column(name = "gender", unique = false)
    private String gender;

    public Staff() {

    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
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

    public void setRole(String role) {
        this.role = role;
    }

    public Long getStaffID() {
        return staffID;
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

    public String getRole() {
        return role;
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
