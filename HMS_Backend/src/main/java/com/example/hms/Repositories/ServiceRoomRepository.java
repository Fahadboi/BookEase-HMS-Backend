package com.example.hms.Repositories;


import com.example.hms.Entities.ServiceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRoomRepository extends JpaRepository<ServiceRoom, Long> {

    @Query(value = "SELECT s FROM ServiceRoom s WHERE s.booking.bookingId = :bookingId")
    List<ServiceRoom> findAllByBookingId(@Param("bookingId") Long bookingId);
}
