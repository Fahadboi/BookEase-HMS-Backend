package com.example.hms.Services;

import com.example.hms.Entities.Billing;
import com.example.hms.Entities.Invoice;
import com.example.hms.Repositories.BillingRepository;
import com.example.hms.Repositories.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private BillingRepository billingRepository;

    public Invoice generateInvoice(Long billingId) {
        Billing billing = billingRepository.findById(billingId).orElseThrow(() -> new EntityNotFoundException("Billing ID not Found: " + billingId));
        Invoice oldInvoice = invoiceRepository.findInvoiceByBillingId(billingId);

        if(oldInvoice != null){
            if(Objects.equals(billing.getBillingStatus(), "Finalized")){
                oldInvoice.setIssuedDate(billing.getBooking().getCheckOut());
            }
            oldInvoice.setDueDate(billing.getBooking().getCheckOut().plusDays(1));
            oldInvoice.setTotalAmount(billing.getTotalDue());
            return invoiceRepository.save(oldInvoice);
        }

        Invoice newInvoice = new Invoice();
        newInvoice.setBilling(billing);
        newInvoice.setIssuedDate(LocalDate.now());
        newInvoice.setDueDate(billing.getBooking().getCheckOut().plusDays(1));
        newInvoice.setTotalAmount(billing.getTotalDue());
        newInvoice.setInvoiceStatus("Unpaid");
        return invoiceRepository.save(newInvoice);
    }

    public List<Invoice> getAllInvoices() {
        if(invoiceRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No invoices found");
        }
        return invoiceRepository.findAll();
    }
}
