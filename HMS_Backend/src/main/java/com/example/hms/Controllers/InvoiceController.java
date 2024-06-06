package com.example.hms.Controllers;

import com.example.hms.Entities.Invoice;
import com.example.hms.Services.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generateInvoice/{billingId}")
    public ResponseEntity<?> generateInvoice(@PathVariable Long billingId){
        try{
            Invoice newInvoice = invoiceService.generateInvoice(billingId);
            return ResponseEntity.ok().body(newInvoice);
        } catch(EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllInvoices")
    public ResponseEntity<?> getAllInvoices(){
        try{
            List<Invoice> invoiceList = invoiceService.getAllInvoices();
            return ResponseEntity.ok().body(invoiceList);
        } catch(EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
