package com.example.hms.Services;

import com.example.hms.Entities.Staff;
import com.example.hms.Repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    public StaffRepository staffRepository;

    public boolean isEmailAlreadyRegistered(String email) {
        return staffRepository.existsByEmail(email);
    }

    public Staff getStaffByID(Long staffID) {
        return staffRepository.getStaffByStaffID(staffID);
    }

    public Staff registerStaff(Staff staff) {
        if(staff.getPassword().length() < 5){
            return null;
        }
        return staffRepository.save(staff);
    }

    public Staff loginStaff(String email, String password) {
        if(!isEmailAlreadyRegistered(email)){
            return null;
        }

        Staff staff = staffRepository.getStaffByEmail(email);
        if(!Objects.equals(staff.getPassword(), password)){
            return null;
        }

        return staff;

    }

    public boolean changePassword(Long staffID, String newPassword) {
        Staff staff = staffRepository.getStaffByStaffID(staffID);
        if (staff != null) {
            staff.setPassword(newPassword);
            staffRepository.save(staff);
            return true;
        }
        else {
            return false;
        }
    }

    public Staff getStaffDetails(Long staffID){
        return staffRepository.getStaffByStaffID(staffID);
    }

    public boolean deleteStaff(Long staffID) {
        Optional<Staff> staff = staffRepository.findById(staffID);
        if (staff.isPresent()) {
            // Staff entity found, delete it
            staffRepository.delete(staff.get());
            return true;
        } else {
            // Staff entity not found
            return false;
        }
    }

    public boolean changeRole(Long staffID, String newRole) {
        Staff staff = staffRepository.getStaffByStaffID(staffID);
        if (staff != null) {
            staff.setRole(newRole);
            staffRepository.save(staff);
            return true;
        } else {
            return false; // Staff not found
        }
    }


    public List<Staff> getAllStaffDetails() {
        return staffRepository.findAll();
    }
}
