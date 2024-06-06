package com.example.hms.Repositories;


import com.example.hms.Entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    boolean existsByEmail(String email);
    Guest getGuestByEmail(String email);
    Guest getGuestByGuestID(Long guestID);
}
