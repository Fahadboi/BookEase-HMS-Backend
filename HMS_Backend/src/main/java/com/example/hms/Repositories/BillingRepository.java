package com.example.hms.Repositories;

import com.example.hms.Entities.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {


    @Query(value = "SELECT (r.price_per_night * :totalDays) + COALESCE(SUM(s.service_price), 0) as totalDue " +
            "FROM Booking b JOIN Room r ON b.room_id = r.roomID " +
            "LEFT JOIN service_room sr ON b.bookingid = sr.booking_id AND sr.service_room_status = 'Completed' " +
            "LEFT JOIN service_type s ON sr.service_type_id = s.service_typeid " +
            "WHERE b.bookingid = :bookingId " +
            "GROUP BY b.bookingid, r.price_per_night", nativeQuery = true)
    double calculateBillAmount(@Param("bookingId") Long bookingId, @Param("totalDays") Long totalDays);

    @Query(value = "SELECT b FROM Billing b WHERE b.booking.bookingId = :bookingId")
    Billing findBillingByBookingId(@Param("bookingId") Long bookingId);

}

