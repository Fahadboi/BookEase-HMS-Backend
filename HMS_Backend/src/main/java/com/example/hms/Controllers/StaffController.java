package com.example.hms.Controllers;

import com.example.hms.Entities.Guest;
import com.example.hms.Entities.Staff;
import com.example.hms.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/registerStaff")
    public ResponseEntity<?> registerStaff(@RequestBody Staff staff) {

        if (staffService.isEmailAlreadyRegistered(staff.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        Staff registeredStaff = staffService.registerStaff(staff);
        if (registeredStaff == null) {
            return ResponseEntity.badRequest().body("Cannot Create Staff Account.");
        } else {
            return ResponseEntity.ok().body(registeredStaff);
        }
    }

    @PostMapping("/loginStaff")
    public ResponseEntity<?> loginStaff(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Staff loginStaff = staffService.loginStaff(email, password);
        if (loginStaff == null) {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        } else {
            return new ResponseEntity<>(loginStaff, HttpStatus.OK);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, Object> requestBody) {
        String staffIDString = (String) requestBody.get("staffID");
        String newPassword = (String) requestBody.get("newPassword");

        if (staffIDString == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Invalid request format");
        }

        Long staffID = null;
        try {
            staffID = Long.parseLong(staffIDString);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid staffID format");
        }

        boolean passwordChanged = staffService.changePassword(staffID, newPassword);
        if (!passwordChanged) {
            return ResponseEntity.badRequest().body("Staff ID Not Found.");
        }

        return ResponseEntity.ok().body("Password changed successfully");
    }


    @GetMapping("/getStaffDetails/{staffID}")
    public ResponseEntity<?> getStaffDetails(@PathVariable Long staffID) {
        Staff staff = staffService.getStaffDetails(staffID);
        if (staff == null) {
            return ResponseEntity.badRequest().body("Cannot retrieve staff Details.");
        } else {
            return new ResponseEntity<>(staff, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllStaffDetails")
    public ResponseEntity<?> getAllStaffDetails() {
        List<Staff> listOfStaff = staffService.getAllStaffDetails();
        if (listOfStaff.isEmpty()) {
            return ResponseEntity.badRequest().body("No Staff was Found.");
        } else {
            return new ResponseEntity<>(listOfStaff, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteStaff/{staffID}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long staffID) {
        // Check if the staff exists
        Staff staff = staffService.getStaffByID(staffID);
        if (staff == null) {
            return ResponseEntity.badRequest().body("Staff not found.");
        }

        // Attempt to delete the staff
        boolean deleteSuccessful = staffService.deleteStaff(staffID);
        if (!deleteSuccessful) {
            return ResponseEntity.badRequest().body("Failed to delete staff.");
        }

        return ResponseEntity.ok().body("Staff deleted successfully.");
    }

    @PatchMapping("/changeRole/{staffID}")
    public ResponseEntity<?> changeRole(@PathVariable Long staffID, @RequestBody String newRole) {
        boolean roleChanged = staffService.changeRole(staffID, newRole);
        if (!roleChanged) {
            return ResponseEntity.badRequest().body("Cannot change role");
        }
        return ResponseEntity.ok().body("Role changed successfully");
    }
}