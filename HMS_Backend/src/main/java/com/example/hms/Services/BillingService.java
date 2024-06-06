package com.example.hms.Services;

import com.example.hms.Entities.Billing;
import com.example.hms.Entities.Booking;
import com.example.hms.Entities.BookingStatus;
import com.example.hms.Repositories.BillingRepository;
import com.example.hms.Repositories.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public Billing generateBillByBookingID(Long bookingID) throws Exception {
        Booking booking = bookingRepository.getBookingByBookingId(bookingID);
        if(booking == null){
            throw new EntityNotFoundException("Booking ID not Found.");
        }

        validateBookingForBilling(booking);
        LocalDate today = LocalDate.now();
        LocalDate effectiveCheckOut = (booking.getCheckOut() != null && (today.isAfter(booking.getCheckOut())  || today.isEqual(booking.getCheckOut()))) ? booking.getCheckOut() : today;
        long totalDays = ChronoUnit.DAYS.between(booking.getCheckIn(), effectiveCheckOut) + 1;
        double totalDue = calculateTotalDue(booking.getBookingId(), totalDays);


        Billing billing = billingRepository.findBillingByBookingId(bookingID);
        if(billing != null){
            if(billing.getBooking().getBookingStatus() == BookingStatus.CHECKED_OUT){
                return billing;
            }
            billing.setTotalDue(totalDue);
            billing.setBillingDate(effectiveCheckOut);
            return billingRepository.save(billing);

        }

        Billing newBilling = new Billing();
        newBilling.setBooking(booking);
        newBilling.setTotalDue(totalDue);
        newBilling.setBillingStatus("Pending");
        newBilling.setBillingDate(effectiveCheckOut);
        return billingRepository.save(newBilling);
    }

    private double calculateTotalDue(Long bookingID, Long totalDays) {
        return billingRepository.calculateBillAmount(bookingID, totalDays);
    }

    @Transactional
    public Billing finalizeBilling(Long bookingID) throws Exception {
        Booking booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new EntityNotFoundException("Booking ID not Found: " + bookingID));


        Billing billing = billingRepository.findBillingByBookingId(bookingID);

        if(billing == null){
            throw new EntityNotFoundException("No Billing was found with booking ID: " + bookingID);
        }

        LocalDate todayDate = LocalDate.now();
        validateBookingForBilling(booking);
        LocalDate effectiveCheckOut = (booking.getCheckOut() != null && (todayDate.isAfter(booking.getCheckOut())  || todayDate.isEqual(booking.getCheckOut()))) ? booking.getCheckOut() : todayDate;
        long totalDays = ChronoUnit.DAYS.between(booking.getCheckIn(), effectiveCheckOut) + 1;
        double totalDue = calculateTotalDue(booking.getBookingId(), totalDays);

        billing.setTotalDue(totalDue);
        billing.setBillingStatus("Finalized");
        billing.setBillingDate(effectiveCheckOut);
        return billingRepository.save(billing);
    }

    private void validateBookingForBilling(Booking booking) throws Exception {
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new Exception("Bill cannot be generated/finalized for the booking ID " + booking.getBookingId() + " because the booking was cancelled.");
        }


        LocalDate today = LocalDate.now();
        if (today.isBefore(booking.getCheckIn())) {
            throw new Exception("Bill cannot be generated/finalized before check-in for booking ID " + booking.getBookingId());
        }
    }

}
