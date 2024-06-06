package com.example.hms.Repositories;

import com.example.hms.Entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    ServiceType getServiceTypeByServiceTypeID(Long serviceTypeID);
}
