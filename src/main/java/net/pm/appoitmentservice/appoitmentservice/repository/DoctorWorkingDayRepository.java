package net.pm.appoitmentservice.appoitmentservice.repository;

import net.pm.appoitmentservice.appoitmentservice.entity.DoctorWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DoctorWorkingDayRepository extends JpaRepository<DoctorWorkingDay, Long> {
    List<DoctorWorkingDay> findByDoctor_Id(Long doctorId);
    List<DoctorWorkingDay> findByDoctor_IdAndDate(Long doctorId, LocalDate date);
}
