package com.example.hms.Controllers;

import com.example.hms.Entities.Booking;
import com.example.hms.Entities.Invoice;
import com.example.hms.Services.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(@RequestParam Long roomId, @RequestParam Long guestId,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        try {
            Booking booking = bookingService.createBooking(roomId, guestId, checkIn, checkOut);
            if(booking == null) {
                return ResponseEntity.badRequest().body("Book is null");
            }
            return ResponseEntity.ok().body(booking);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("An error occurred while processing your request.");
        }
    }


    @PatchMapping("/extend-checkout-date/{bookingId}")
    public ResponseEntity<?> extendCheckout(@PathVariable Long bookingId, @RequestParam int extendDays) {
        try {
            boolean success = bookingService.extendCheckOutDate(bookingId, extendDays);
            return ResponseEntity.ok().body("Checkout date extended successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        try {
            boolean success = bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok().body("Booking cancelled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/booked-dates/{roomNo}")
    public ResponseEntity<?> getBookedDates(@PathVariable int roomNo) {
        try {
            Set<String> bookedDates = bookingService.getBookedDates(roomNo);
            return ResponseEntity.ok().body(bookedDates);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while processing your request: " + e.getMessage());
        }
    }

    @GetMapping("/getBookingsOfRoom/{roomNo}")
    public  ResponseEntity<?> getBookingsOfRoom(@PathVariable int roomNo){
        try {
            List<Booking> listOfRoomBooking = bookingService.findAllBookingsOfRoom(roomNo);
            return ResponseEntity.ok().body(listOfRoomBooking);
        }catch(EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/getAllBookings")
    public  ResponseEntity<?> getAllBookings(){
        try {
            List<Booking> allRoomBookings = bookingService.getAllBookings();
            return ResponseEntity.ok().body(allRoomBookings);
        }catch(EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PatchMapping("/initiateCheckOut/{bookingID}")
    public ResponseEntity<?> initiateCheckOut(@PathVariable Long bookingID) {
        try{
            Invoice invoice = bookingService.initiateCheckOutAndGenerateInvoice(bookingID);
            return ResponseEntity.ok().body(invoice);
        } catch(EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
