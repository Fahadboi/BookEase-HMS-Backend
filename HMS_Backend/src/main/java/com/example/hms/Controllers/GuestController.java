package com.example.hms.Controllers;

import com.example.hms.Entities.Guest;
import com.example.hms.Services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping("/registerGuest")
    public ResponseEntity<?> registerGuest(@RequestBody Guest guest) {

        if (guestService.isEmailAlreadyRegistered(guest.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        Guest registeredGuest = guestService.registerGuest(guest);
        if (registeredGuest == null) {
            return ResponseEntity.badRequest().body("Cannot Create Guest Account.");
        } else {
            return new ResponseEntity<>(registeredGuest, HttpStatus.OK);
        }
    }

    @PostMapping("/loginGuest")
    public ResponseEntity<?> loginGuest(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Guest loginGuest = guestService.loginGuest(email, password);
        if (loginGuest == null) {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        } else {
            return new ResponseEntity<>(loginGuest, HttpStatus.OK);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, Object> requestBody) {
        String guestIDString = (String) requestBody.get("guestID");
        String newPassword = (String) requestBody.get("newPassword");

        if (guestIDString == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Invalid request format");
        }

        Long guestID = null;
        try {
            guestID = Long.parseLong(guestIDString);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid guestID format");
        }

        boolean passwordChanged = guestService.changePassword(guestID, newPassword);
        if (!passwordChanged) {
            return ResponseEntity.badRequest().body("Guest ID Not Found.");
        }

        return ResponseEntity.ok().body("Password changed successfully");
    }


    @GetMapping("/getGuestDetails/{guestID}")
    public ResponseEntity<?> getGuestDetails(@PathVariable Long guestID) {
        Guest guest = guestService.getGuestDetails(guestID);
        if (guest == null) {
            return ResponseEntity.badRequest().body("Cannot retrieve guest Details.");
        } else {
            return new ResponseEntity<>(guest, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllGuestDetails")
    public ResponseEntity<?> getAllGuestDetails() {
        List<Guest> guest = guestService.getAllGuestDetails();
        if (guest.isEmpty()) {
            return ResponseEntity.badRequest().body("No guest was Found.");
        } else {
            return new ResponseEntity<>(guest, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteGuest/{guestID}")
    public ResponseEntity<?> deleteGuest(@PathVariable Long guestID) {
        // Check if the guest exists
        Guest guest = guestService.getGuestByID(guestID);
        if (guest == null) {
            return ResponseEntity.badRequest().body("Guest not found.");
        }

        // Attempt to delete the guest
        boolean deleteSuccessful = guestService.deleteGuest(guestID);
        if (!deleteSuccessful) {
            return ResponseEntity.badRequest().body("Failed to delete guest.");
        }

        return ResponseEntity.ok().body("Guest deleted successfully.");
    }
}