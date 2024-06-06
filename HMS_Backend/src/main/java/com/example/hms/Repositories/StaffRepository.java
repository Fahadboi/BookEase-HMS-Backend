package com.example.hms.Repositories;


import com.example.hms.Entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByEmail(String email);
    Staff getStaffByEmail(String email);
    Staff getStaffByStaffID(Long staffID);
}
