package com.example.hms.Repositories;

import com.example.hms.Entities.Booking;
import com.example.hms.Entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.room.roomNo = :roomNo AND b.bookingStatus <> 'CANCELLED'")
    List<Booking> findBookingsByRoomNo(@Param("roomNo") int roomNo);

    Booking getBookingByBookingId(Long bookingId);

    // Query to check for booking overlaps
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.room.roomNo = :roomNo AND b.bookingStatus <> 'CANCELLED' AND NOT (b.checkOut <= :checkIn OR b.checkIn >= :checkOut)")
    boolean existsByRoomNoAndOverlappingDates(@Param("roomNo") int roomNo, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

    @Query("SELECT i FROM Invoice i JOIN Billing bi ON i.billing.billingID = bi.billingID JOIN Booking b ON bi.booking.bookingId = :bookingID")
    Invoice alreadyCheckOut(@Param("bookingID") Long bookingID);
}
