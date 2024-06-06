package com.example.hms.Services;

import com.example.hms.Entities.Guest;
import com.example.hms.Repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    public GuestRepository guestRepository;

    public boolean isEmailAlreadyRegistered(String email) {
        return guestRepository.existsByEmail(email);
    }

    public Guest getGuestByID(Long guestID) {
        return guestRepository.getGuestByGuestID(guestID);
    }

    public Guest registerGuest(Guest guest) {
        if(guest.getPassword().length() < 5){
            return null;
        }
        return guestRepository.save(guest);
    }

    public Guest loginGuest(String email, String password) {
        if(!isEmailAlreadyRegistered(email)){
            return null;
        }

        Guest guest = guestRepository.getGuestByEmail(email);
        if(!Objects.equals(guest.getPassword(), password)){
            return null;
        }

        return guest;

    }

    public boolean changePassword(Long guestID, String newPassword) {
        Guest guest = guestRepository.getGuestByGuestID(guestID);
        if (guest != null) {
            guest.setPassword(newPassword);
            guestRepository.save(guest);
            return true;
        }
        else {
            return false;
        }
    }

    public Guest getGuestDetails(Long guestID){
        return guestRepository.getGuestByGuestID(guestID);
    }

    public boolean deleteGuest(Long guestID) {
        Optional<Guest> guest = guestRepository.findById(guestID);
        if (guest.isPresent()) {
            // Guest entity found, delete it
            guestRepository.delete(guest.get());
            return true;
        } else {
            // Guest entity not found
            return false;
        }
    }


    public List<Guest> getAllGuestDetails() {
        return guestRepository.findAll();
    }
}
