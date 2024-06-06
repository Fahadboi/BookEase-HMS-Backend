package com.example.hms.Services;

import com.example.hms.Entities.ServiceType;
import com.example.hms.Repositories.ServiceTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public ServiceType createServiceType(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public ServiceType getServiceType(Long serviceTypeID) {
        ServiceType serviceType = serviceTypeRepository.getServiceTypeByServiceTypeID(serviceTypeID);
        if(serviceType == null){
            throw new EntityNotFoundException("Service Type with ID: " + serviceTypeID + " not found.");
        }
        return serviceType;
    }

    public List<ServiceType> getAllServiceType() {
        List<ServiceType> serviceType = serviceTypeRepository.findAll();
        if(serviceType.isEmpty()){
            throw new EntityNotFoundException("No Service type was found.");
        }
        return serviceType;
    }

    public ServiceType updateServiceType(Long serviceTypeID, ServiceType serviceType) {
        ServiceType serviceType1 = serviceTypeRepository.getServiceTypeByServiceTypeID(serviceTypeID);
        if (serviceType1 == null) {
            throw new EntityNotFoundException("Service Type with ID: " + serviceTypeID + " not found.");
        }
        serviceType1.setServiceName(serviceType.getServiceName());
        serviceType1.setServicePrice(serviceType.getServicePrice());
        serviceType1.setServiceDescription(serviceType.getServiceDescription());
        return serviceTypeRepository.save(serviceType1);
    }

    @Transactional
    public boolean deleteServiceType(Long serviceTypeID) {
        ServiceType serviceType = serviceTypeRepository.getServiceTypeByServiceTypeID(serviceTypeID);
        if(serviceType == null){
            throw new EntityNotFoundException("Service Type with ID: " + serviceTypeID + " not found.");
        }
        serviceTypeRepository.delete(serviceType);
        return true;
    }
}
