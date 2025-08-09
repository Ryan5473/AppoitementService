package net.pm.appoitmentservice.appoitmentservice.repository;

import net.pm.appoitmentservice.appoitmentservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
}
