package com.example.hms.Services;

import com.example.hms.Entities.Invoice;
import com.example.hms.Entities.Payment;
import com.example.hms.Repositories.InvoiceRepository;
import com.example.hms.Repositories.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Transactional
    public Payment handlePayment(Long invoiceID, double amount) throws Exception {
        Invoice invoice = invoiceRepository.findById(invoiceID).orElseThrow(() -> new EntityNotFoundException("No Invoice was Found with Invoice ID: " + invoiceID));
        if(Objects.equals(invoice.getInvoiceStatus(), "Paid")) {
            throw new Exception("Payment already completed for Invoice ID: " + invoice.getInvoiceID());
        }

        if (amount < invoice.getTotalAmount()) {
            throw new Exception("Insufficient payment amount. Full payment required.");
        }
        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setPaymentDate(LocalDate.now());
        payment.setAmountPaid(amount);
        invoice.setInvoiceStatus("Paid");
        invoiceRepository.save(invoice);
        return paymentRepository.save(payment);
    }


    public List<Payment> getAllPaymentDetails() {
        return paymentRepository.findAll();  // Returns an empty list if no payments found
    }
}
