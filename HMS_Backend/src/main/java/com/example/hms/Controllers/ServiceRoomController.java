package com.example.hms.Controllers;

import com.example.hms.Entities.ServiceRoom;
import com.example.hms.Entities.ServiceRoomDTO;
import com.example.hms.Services.ServiceRoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/roomService")
public class ServiceRoomController {

    @Autowired
    private ServiceRoomService serviceRoomService;

    @PostMapping("/createRoomService")
    public ResponseEntity<?> createRoomService(@RequestBody ServiceRoomDTO serviceRoomDTO){
        try {
            ServiceRoom roomService = serviceRoomService.createRoomService(serviceRoomDTO);
            return ResponseEntity.ok().body(roomService);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Change to notFound
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/assignStaffToRoomService/{serviceRoomId}/{staffId}")
    public ResponseEntity<?> assignStaffToRoomService(@PathVariable Long serviceRoomId, @PathVariable Long staffId){
        try {
            ServiceRoom serviceRoom = serviceRoomService.assignStaffToRoomService(serviceRoomId, staffId);
            return ResponseEntity.ok().body(serviceRoom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PatchMapping("/cancelRoomService/{serviceRoomId}")
    public ResponseEntity<?> cancelRoomService(@PathVariable Long serviceRoomId){
        try {
            ServiceRoom serviceRoom = serviceRoomService.cancelRoomService(serviceRoomId);
            return ResponseEntity.ok().body(serviceRoom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PatchMapping("/roomServiceCompleted/{serviceRoomId}")
    public ResponseEntity<?> roomServiceCompleted(@PathVariable Long serviceRoomId) {
        try {
            ServiceRoom serviceRoom = serviceRoomService.roomServiceCompleted(serviceRoomId);
            return ResponseEntity.ok().body(serviceRoom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteRoomService/{serviceRoomId}")
    public ResponseEntity<?> deleteRoomService(@PathVariable Long serviceRoomId) {
        try {
            boolean deleteRoomService = serviceRoomService.deleteRoomService(serviceRoomId);
            if(!deleteRoomService){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("Room Service Deleted Successfully.");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log the exception or handle specific cases if needed
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getRoomService/{serviceRoomId}")
    public ResponseEntity<?> getRoomService(@PathVariable Long serviceRoomId) {
        try {
            ServiceRoom serviceRoom = serviceRoomService.getRoomServiceById(serviceRoomId);
            return ResponseEntity.ok().body(serviceRoom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log the exception or handle specific cases if needed
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getAllRoomService")
    public ResponseEntity<?> getAllRoomService() {
        try {
            List<ServiceRoom> serviceRoom = serviceRoomService.getAllServiceRooms();
            if(serviceRoom.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(serviceRoom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log the exception or handle specific cases if needed
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getAllRoomServiceByBookingID/{BookingID}")
    public ResponseEntity<?> getAllRoomServiceByBookingID(@PathVariable Long BookingID) {
        try {
            List<ServiceRoom> serviceRoom = serviceRoomService.getAllServiceRoomsByBookingID(BookingID);
            if(serviceRoom.isEmpty()){
                return ResponseEntity.badRequest().body("No Room Services Requested.");
            }
            return ResponseEntity.ok().body(serviceRoom);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log the exception or handle specific cases if needed
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }



}


