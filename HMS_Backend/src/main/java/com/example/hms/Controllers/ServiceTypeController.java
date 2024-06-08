package com.example.hms.Controllers;

import com.example.hms.Entities.ServiceType;
import com.example.hms.Services.ServiceTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;

@CrossOrigin(origins = "https://book-ease-hms-frontend-es8w54962.vercel.app")
@RestController
@RequestMapping("/api/service")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    @PostMapping("/createService")
    public ResponseEntity<?> createService(@RequestBody ServiceType serviceType){
        try{
            ServiceType serviceType1 = serviceTypeService.createServiceType(serviceType);
            return ResponseEntity.ok().body(serviceType1);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getRoomService/{serviceTypeID}")
    public ResponseEntity<?> getRoomService(@PathVariable Long serviceTypeID){
        try{
            ServiceType serviceType = serviceTypeService.getServiceType(serviceTypeID);
            return ResponseEntity.ok().body(serviceType);
        }catch(EntityNotFoundException e){
            return ResponseEntity.ok().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllServiceTypes")
    public ResponseEntity<?> getAllServiceType(){
        try{
            List<ServiceType> serviceType = serviceTypeService.getAllServiceType();
            if (serviceType.isEmpty()) {
                return ResponseEntity.noContent().build();  // Return 204 NO CONTENT status.
            }
            return ResponseEntity.ok().body(serviceType);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/updateService/{serviceTypeID}")
    public ResponseEntity<?> updateService(@PathVariable Long serviceTypeID, @RequestBody ServiceType serviceType){
        try{
            ServiceType serviceType1 = serviceTypeService.updateServiceType(serviceTypeID, serviceType);
            return ResponseEntity.ok().body(serviceType1);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteService/{serviceTypeID}")
    public ResponseEntity<?> deleteService(@PathVariable Long serviceTypeID) {
        try {
            boolean deleteServiceType = serviceTypeService.deleteServiceType(serviceTypeID);
            return ResponseEntity.ok().body("Service Type Deleted Successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
