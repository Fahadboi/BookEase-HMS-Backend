package com.example.hms.Services;

import com.example.hms.Entities.*;
import com.example.hms.Repositories.BookingRepository;
import com.example.hms.Repositories.ServiceRoomRepository;
import com.example.hms.Repositories.ServiceTypeRepository;
import com.example.hms.Repositories.StaffRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServiceRoomService {

    @Autowired
    private ServiceRoomRepository serviceRoomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private StaffRepository staffRepository;

    @Transactional
    public ServiceRoom createRoomService(ServiceRoomDTO serviceRoomDTO) throws Exception {
        if (serviceRoomDTO == null) {
            throw new IllegalArgumentException("ServiceRoomDTO must not be null.");
        }

        Optional<ServiceType> serviceTypeOpt = serviceTypeRepository.findById(serviceRoomDTO.getServiceTypeId());
        Optional<Booking> bookingOpt = bookingRepository.findById(serviceRoomDTO.getBookingId());

        if (!serviceTypeOpt.isPresent()) {
            throw new EntityNotFoundException("Service Type with ID " + serviceRoomDTO.getServiceTypeId() + " not found.");
        }

        if (bookingOpt.isEmpty()) {
            throw new EntityNotFoundException("Booking with ID " + serviceRoomDTO.getBookingId() + " not found.");
        }

        LocalDate checkInDate = bookingOpt.get().getCheckIn();
        LocalDate checkOutDate = bookingOpt.get().getCheckOut();

        if(serviceRoomDTO.getServiceRoomDate().isBefore(checkInDate)){
            throw new IllegalArgumentException("Room service can not ordered before check in.");
        }
        if(serviceRoomDTO.getServiceRoomDate().isAfter(checkOutDate)){
            throw new IllegalArgumentException("Room service can not ordered after the check out.");
        }

        ServiceRoom serviceRoom = new ServiceRoom();
        serviceRoom.setServiceType(serviceTypeOpt.get());
        serviceRoom.setBooking(bookingOpt.get());
        serviceRoom.setServiceRoomDate(serviceRoomDTO.getServiceRoomDate());
        serviceRoom.setServiceRoomStatus("Requested"); // Consider making this a constant or enum

        return serviceRoomRepository.save(serviceRoom);
    }

    public ServiceRoom assignStaffToRoomService(Long serviceRoomId, Long staffId) throws Exception {
        Staff staff = staffRepository.getStaffByStaffID(staffId);
        ServiceRoom serviceRoom = serviceRoomRepository.findById(serviceRoomId).orElseThrow(() -> new EntityNotFoundException("Service Room with ID " + serviceRoomId + " not found."));

        if(staff == null){
            throw new EntityNotFoundException("Staff with ID " + staffId + " not found.");
        }

        if(!serviceRoom.getServiceRoomStatus().equals("Requested")){
            throw new Exception("Staff can only be assigned to room service Status : 'Requested'." );
        }
        LocalDate today = LocalDate.now();

        if(today.isAfter(serviceRoom.getBooking().getCheckOut())){
            throw new Exception("Cannot assign staff after the check-out date of the booking");
        }
        serviceRoom.setStaff(staff);
        return serviceRoomRepository.save(serviceRoom);
    }


    public ServiceRoom cancelRoomService(Long serviceRoomId) throws Exception {
        ServiceRoom serviceRoom = serviceRoomRepository.findById(serviceRoomId).orElseThrow(() -> new EntityNotFoundException("Service Room with ID " + serviceRoomId + " not found."));

        if(serviceRoom.getServiceRoomStatus().equals("Cancelled")){
            throw new Exception("Room Service is Already Cancelled.");
        }

        if(serviceRoom.getServiceRoomStatus().equals("Completed")){
            throw new Exception("Room Service is Already Completed, so it cannot be cancelled.");
        }

        serviceRoom.setServiceRoomStatus("Cancelled");
        return serviceRoomRepository.save(serviceRoom);
    }

    public ServiceRoom roomServiceCompleted(Long serviceRoomId) throws Exception {
        ServiceRoom serviceRoom = serviceRoomRepository.findById(serviceRoomId).orElseThrow(() -> new EntityNotFoundException("Service Room with ID " + serviceRoomId + " not found."));

        if(serviceRoom.getServiceRoomStatus().equals("Cancelled")){
            throw new Exception("Cannot change room status of service room, as it is Cancelled.");
        }

        if(serviceRoom.getServiceRoomStatus().equals("Completed")){
            throw new Exception("Room Service is Already Completed");
        }

        if(serviceRoom.getStaff() == null){
            throw new EntityNotFoundException("No staff is assigned to this room service, so it cannot be set to 'Completed' status.");
        }

        serviceRoom.setServiceRoomStatus("Completed");
        return serviceRoomRepository.save(serviceRoom);

    }

    public boolean deleteRoomService(Long serviceRoomId) throws Exception {
        ServiceRoom serviceRoom = serviceRoomRepository.findById(serviceRoomId).orElseThrow(() -> new EntityNotFoundException("Service Room with ID " + serviceRoomId + " not found."));
        if(Objects.equals(serviceRoom.getServiceRoomStatus(), "Completed")){
            throw new Exception("Room Service is Already Completed, so it cannot be deleted.");
        }
        serviceRoomRepository.delete(serviceRoom);
        return true;
    }

    public ServiceRoom getRoomServiceById(Long serviceRoomId) {
        ServiceRoom serviceRoom = serviceRoomRepository.findById(serviceRoomId).orElseThrow(() -> new EntityNotFoundException("Service Room with ID " + serviceRoomId + " not found."));
        return serviceRoom;
    }

    public List<ServiceRoom> getAllServiceRooms() {
        return serviceRoomRepository.findAll();
    }

    public List<ServiceRoom> getAllServiceRoomsByBookingID(Long bookingID) {
        return serviceRoomRepository.findAllByBookingId(bookingID);
    }
}
