package com.example.hms.Services;

import com.example.hms.Entities.*;
import com.example.hms.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private BillingRepository billingRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;


    //Create a booking for room.
    @Transactional
    public Booking createBooking(Long roomId, Long guestId, LocalDate checkIn, LocalDate checkOut) throws EntityNotFoundException, IllegalArgumentException {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("Room not found."));
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new EntityNotFoundException("Guest not found."));

        LocalDate today = LocalDate.now();
        if (checkIn.isBefore(today)) {
            throw new IllegalArgumentException("Check-in date cannot be before today's date.");
        }
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before the check-out date.");
        }

        if (!isRoomAvailable(room.getRoomNo(), checkIn, checkOut)) {
            throw new IllegalArgumentException("The selected room is not available for the given dates.");
        }

        System.out.println("Room is available for the selected dates.");
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setGuest(guest);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        long totalDays = ChronoUnit.DAYS.between(checkIn, checkOut) + 1;
        booking.setTotalDays((int) totalDays);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        room.setRoomStatus("Occupied");
        roomRepository.save(room);
        bookingRepository.save(booking);
        return booking;
    }

    //Check if the room is available on the provided dates. Checking dates Overlap in this function.
    private boolean isRoomAvailable(int roomNo, LocalDate checkIn, LocalDate checkOut) {
        return !bookingRepository.existsByRoomNoAndOverlappingDates(roomNo, checkIn, checkOut);
    }

    //Cancel the booking using bookingID
    public boolean cancelBooking(Long bookingId) throws IllegalArgumentException {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found.");
        }
        Booking booking = bookingOpt.get();
        LocalDate presentDate = LocalDate.now();

        // Ensure the cancellation is before the check-in date
        if (presentDate.isEqual(booking.getCheckIn()) || presentDate.isAfter(booking.getCheckIn())) {
            throw new IllegalArgumentException("Booking cannot be cancelled on or after the check-in date.");
        }

        if (booking.getBookingStatus().equals(BookingStatus.CHECKED_OUT)) {
            throw new IllegalArgumentException("Booking Has Already Been Checked Out.");
        }

        if (!booking.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Only confirmed bookings can be cancelled.");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        return true;
    }

    @Transactional
    public boolean extendCheckOutDate(Long bookingId, int extendDays) throws IllegalArgumentException {
        if (extendDays <= 0) {
            throw new IllegalArgumentException("Extension days must be greater than zero.");
        }

        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking does not exist.");
        }

        Booking booking = bookingOpt.get();
        LocalDate newCheckOut = booking.getCheckOut().plusDays(extendDays);

        if (LocalDate.now().isAfter(booking.getCheckOut())) {
            throw new IllegalArgumentException("Cannot extend a checkout date that has already passed.");
        }

        if(booking.getBookingStatus().equals(BookingStatus.CHECKED_OUT)){
            throw new IllegalArgumentException("Booking has already been checked out, So cannot Extend the check-out date.");
        }

        if (!isRoomAvailable(booking.getRoom().getRoomNo(), booking.getCheckOut().plusDays(1), newCheckOut)) {
            throw new IllegalArgumentException("The room is not available for the requested extension period.");
        }

        booking.setCheckOut(newCheckOut);
        long totalDays = ChronoUnit.DAYS.between(booking.getCheckIn(), newCheckOut);
        booking.setTotalDays((int) totalDays);
        bookingRepository.save(booking);
        return true;
    }

    //Fetch all the booking dates
    public Set<String> getBookedDates(int roomNo) throws EntityNotFoundException {
        List<Booking> bookings = bookingRepository.findBookingsByRoomNo(roomNo);
        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("No bookings found for room number: " + roomNo);
        }
        Set<LocalDate> bookedDates = new HashSet<>();
        for (Booking booking : bookings) {
            LocalDate date = booking.getCheckIn();
            while (!date.isAfter(booking.getCheckOut())) {
                bookedDates.add(date);
                date = date.plusDays(1);
            }
        }
        return bookedDates.stream().map(LocalDate::toString).collect(Collectors.toSet());
    }


    public List<Booking> findAllBookingsOfRoom(int roomNo) {
        List<Booking> listOfBooking = bookingRepository.findBookingsByRoomNo(roomNo);
        if(listOfBooking.isEmpty()){
            throw new EntityNotFoundException("Bookings not found.");
        }
        return listOfBooking;
    }

    public List<Booking> getAllBookings() {
        List<Booking> listOfBooking = bookingRepository.findAll();
        if(listOfBooking.isEmpty()){
            throw new EntityNotFoundException("No bookings available.");
        }
        return listOfBooking;
    }

    @Transactional
    public Invoice initiateCheckOutAndGenerateInvoice(Long bookingID) throws Exception{
        Booking booking = bookingRepository.getBookingByBookingId(bookingID);
        if(booking == null){
            throw new EntityNotFoundException("Booking not found.");
        }

        if(booking.getBookingStatus().equals(BookingStatus.CHECKED_OUT)){
            throw new EntityNotFoundException("This Booking has been Checked-out. So, you cannot initiate the checkout.");
        }

        if(booking.getBookingStatus().equals(BookingStatus.CANCELLED)){
            throw new EntityNotFoundException("This Booking has been cancelled. So, you cannot initiate the checkout.");
        }

        if(checkIfGuestAlreadyCheckOut(bookingID)){
            throw new EntityNotFoundException("Booking is already checked out.");
        }

        if(LocalDate.now().isBefore(booking.getCheckIn())){
            throw new EntityNotFoundException("You cannot check out before the check-in date.");
        }

        if(LocalDate.now().isBefore(booking.getCheckOut())){
            booking.setCheckOut(LocalDate.now());
            long totalDays = ChronoUnit.DAYS.between(booking.getCheckIn(), LocalDate.now()) + 1;
            booking.setTotalDays((int)totalDays);
            booking.setBookingStatus(BookingStatus.CHECKED_OUT);
            bookingRepository.save(booking);
        }

        if(LocalDate.now().isAfter(booking.getCheckOut()) || LocalDate.now().isEqual(booking.getCheckOut())){
            long totalDays = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut()) + 1;
            booking.setTotalDays((int)totalDays);
            booking.setBookingStatus(BookingStatus.CHECKED_OUT);
            bookingRepository.save(booking);
        }

        Billing billing = billingRepository.findBillingByBookingId(bookingID);

        if(billing == null){
            System.out.println("Billing Not Found : " + billing);
            System.out.println("Creating New Billing");
            billing = new Billing();
            System.out.println("Set Billing Booking: " + booking);
            billing.setBooking(booking);
            System.out.println("Billing's booking has been Set : " + billing);

        }

        billing.setBillingDate(LocalDate.now());
        billing.setTotalDue(billingRepository.calculateBillAmount(bookingID, (long) booking.getTotalDays()));
        billing.setBillingStatus("Finalized");
        billingRepository.save(billing);

        Invoice invoice = invoiceRepository.findInvoiceByBillingId(billing.getBillingID());
        if(invoice == null){
            System.out.println("Invoice Not Found : " + invoice);
            System.out.println("Creating New Invoice");
            invoice = new Invoice();
            invoice.setIssuedDate(LocalDate.now());
            invoice.setInvoiceStatus("Unpaid");
            System.out.println("Printing new Invoice : " + invoice);
        }

        invoice.setBilling(billing);
        invoice.setDueDate(booking.getCheckOut().plusDays(1));
        invoice.setTotalAmount(billing.getTotalDue());
        System.out.println("Sending Invoice to the frontend : " + invoice);
        return invoiceRepository.save(invoice);
    }

    private boolean checkIfGuestAlreadyCheckOut(Long bookingID) {
        Invoice invoice = bookingRepository.alreadyCheckOut(bookingID);
        if(invoice == null){
            return false;
        }
        return Objects.equals(invoice.getBilling().getBillingStatus(), "Finalized");
    }
}
