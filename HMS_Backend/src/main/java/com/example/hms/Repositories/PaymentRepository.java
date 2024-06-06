package com.example.hms.Repositories;

import com.example.hms.Entities.Invoice;
import com.example.hms.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT p FROM Payment p WHERE p.invoice.invoiceID = :invoiceID")
    Payment getPaymentDetailsByInvoiceID(@Param("invoiceID") Long invoiceID);
}
