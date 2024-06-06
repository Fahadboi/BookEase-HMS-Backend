package com.example.hms.Services;

import com.example.hms.Entities.Room;
import com.example.hms.Entities.Staff;
import com.example.hms.Repositories.GuestRepository;
import com.example.hms.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    public RoomRepository roomRepository;

    public boolean existsByRoomNo(int roomNo) {
        return roomRepository.existsByRoomNo(roomNo);
    }

    public Room createRoom(Room room) {
        if(!Objects.equals(room.getRoomStatus(), "Available") && !Objects.equals(room.getRoomStatus(), "Occupied")){
            return null;
        }
        return roomRepository.save(room);
    }

    public Room getRoomDetailsByRoomNo(int roomNo){
        return roomRepository.getRoomByRoomNo(roomNo);
    }

    public List<Room> getAllRoomsDetail(){
        return roomRepository.findAll();
    }

    public List<Room> getAllRoomsByRoomStatus(String roomStatus) {
        return roomRepository.findByRoomStatus(roomStatus);
    }

    public Room updateRoomStatus(int roomNo, String roomStatus) {
        Room room = roomRepository.getRoomByRoomNo(roomNo);
        if (room == null) {
            return null;
        }
        room.setRoomStatus(roomStatus);
        return roomRepository.save(room);
    }

    public boolean deleteRoomByRoomNo(int roomNo) {
        Optional<Room> room = Optional.ofNullable(roomRepository.getRoomByRoomNo(roomNo));
        if (room.isPresent()) {
            roomRepository.delete(room.get());
            return true;
        } else {
            return false;
        }
    }
}
