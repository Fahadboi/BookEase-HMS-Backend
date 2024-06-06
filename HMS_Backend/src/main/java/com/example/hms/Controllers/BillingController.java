package com.example.hms.Controllers;

import com.example.hms.Entities.Billing;
import com.example.hms.Services.BillingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/generateBill/{bookingID}")
    public ResponseEntity<?> generateBill(@PathVariable Long bookingID){
        try{
            Billing newBill = billingService.generateBillByBookingID(bookingID);
            return ResponseEntity.ok().body(newBill);
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/finalizeBill/{bookingID}")
    public ResponseEntity<?> finalizeBill(@PathVariable Long bookingID){
        try{
            Billing newBill = billingService.finalizeBilling(bookingID);
            return ResponseEntity.ok().body(newBill);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



}
