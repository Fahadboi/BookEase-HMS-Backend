package com.example.hms.Repositories;

import com.example.hms.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByRoomNo(int roomNo);
    Room getRoomByRoomNo(int roomNo);
    List<Room> findByRoomStatus(String roomStatus);
}
