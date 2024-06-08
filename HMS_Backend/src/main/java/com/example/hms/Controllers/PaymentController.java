package com.example.hms.Controllers;

import com.example.hms.Entities.Payment;
import com.example.hms.Services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://book-ease-hms-frontend-es8w54962.vercel.app")
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/makePayment/{invoiceID}")
    public ResponseEntity<?> makePayment(@PathVariable Long invoiceID, @RequestParam("totalAmount") double totalAmount){
        try{
            Payment payment = paymentService.handlePayment(invoiceID, totalAmount);
            return ResponseEntity.ok().body(payment);
        } catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllPayments")
    public ResponseEntity<?> getAllPayments() {
        List<Payment> paymentList = paymentService.getAllPaymentDetails();
        if (paymentList.isEmpty()) {
            return ResponseEntity.noContent().build();  // Returns 204 No Content when the list is empty
        }
        return ResponseEntity.ok(paymentList);  // Returns 200 OK with the payment list
    }
}



