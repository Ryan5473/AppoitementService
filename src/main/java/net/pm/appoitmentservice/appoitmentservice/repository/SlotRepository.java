package net.pm.appoitmentservice.appoitmentservice.repository;

import net.pm.appoitmentservice.appoitmentservice.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository  extends JpaRepository<Slot, Long> {

    // ✅ Use proper joins through WorkingDay
    List<Slot> findByWorkingDay_Doctor_IdAndDate(Long doctorId, String date);

    List<Slot> findByWorkingDay_Doctor_IdAndDateAndBookedFalse(Long doctorId, String date);

    List<Slot> findByWorkingDay_Doctor_Id(Long doctorId);

    List<Slot> findByPatientId(Long patientId);
    // Get all slots for a specific working day ID and a specific doctor ID
    List<Slot> findByWorkingDay_IdAndWorkingDay_Doctor_Id(Long workingDayId, Long doctorId);

}

