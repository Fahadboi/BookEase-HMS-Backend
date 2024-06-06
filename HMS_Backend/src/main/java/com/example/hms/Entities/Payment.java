package com.example.hms.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paymentID")
    private Long paymentID;

    @OneToOne
    @JoinColumn(name = "invoiceId", referencedColumnName = "invoiceID")
    private Invoice invoice;

    @Column(name = "amountPaid")
    private double amountPaid;

    @Column(name = "paymentDate")
    private LocalDate paymentDate;

    public Payment() {
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
