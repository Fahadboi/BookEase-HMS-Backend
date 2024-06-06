package com.example.hms.Repositories;

import com.example.hms.Entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "SELECT i FROM Invoice i WHERE i.billing.billingID = :billingId")
    Invoice findInvoiceByBillingId(@Param("billingId") Long billingId);
}
