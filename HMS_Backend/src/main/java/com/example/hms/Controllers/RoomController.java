package com.example.hms.Controllers;

import com.example.hms.Entities.Room;
import com.example.hms.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "https://book-ease-hms-frontend-es8w54962.vercel.app")
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/createRoom")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        // Check if roomNo already exists
        if (roomService.existsByRoomNo(room.getRoomNo())) {
            return ResponseEntity.badRequest().body("Room with this number already exists.");
        }

        // Save the room
        Room createdRoom = roomService.createRoom(room);
        if (createdRoom == null) {
            return ResponseEntity.badRequest().body("Failed to create room.");
        }

        return ResponseEntity.ok().body(createdRoom);
    }


    @GetMapping("/getRoomDetail/{roomNo}")
    public ResponseEntity<?> getRoomDetails(@PathVariable int roomNo){
        if(!roomService.existsByRoomNo(roomNo)){
            return ResponseEntity.badRequest().body("Room with this number does not exists.");
        }
        Room room = roomService.getRoomDetailsByRoomNo(roomNo);
        if(room == null){
            return ResponseEntity.badRequest().body("Some error occurred.");
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping("/getAllRooms")
    public ResponseEntity<?> getAllRooms(){
        List<Room> rooms = roomService.getAllRoomsDetail();
        if(rooms.isEmpty()){
            return ResponseEntity.badRequest().body("No Rooms Found");
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/getRoomsByStatus/{roomStatus}")
    public ResponseEntity<?> getAllRoomsByStatus(@PathVariable String roomStatus) {
        List<Room> rooms = roomService.getAllRoomsByRoomStatus(roomStatus);
        if (rooms.isEmpty()) {
            return ResponseEntity.badRequest().body("No Rooms Found");
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @PatchMapping("/changeRoomStatus/{roomNo}")
    public ResponseEntity<?> changeRoomStatus(@PathVariable int roomNo, @RequestBody Map<String, String> body) {
        String roomStatus = body.get("roomStatus");

        if(!roomService.existsByRoomNo(roomNo)){
            return ResponseEntity.badRequest().body("Room with this number does not exists.");
        }

        if(!Objects.equals(roomStatus, "Occupied") && !Objects.equals(roomStatus, "Available")){
            return ResponseEntity.badRequest().body("Wrong Room Status Provided");
        }

        Room room = roomService.updateRoomStatus(roomNo, roomStatus);
        if(room == null){
            return ResponseEntity.badRequest().body("Cannot change room status.");
        }

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoom/{roomNo}")
    public ResponseEntity<?> deleteRoom(@PathVariable int roomNo){
        if(!roomService.existsByRoomNo(roomNo)){
            return ResponseEntity.badRequest().body("Room with this number does not exists.");
        }

        boolean roomDeleted = roomService.deleteRoomByRoomNo(roomNo);
        if(!roomDeleted){
            return ResponseEntity.badRequest().body("Cannot delete room.");
        }

        return ResponseEntity.ok().body("Room Deleted Successfully");
    }



}

