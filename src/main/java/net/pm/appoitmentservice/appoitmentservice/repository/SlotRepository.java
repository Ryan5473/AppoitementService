package net.pm.appoitmentservice.appoitmentservice.repository;

import net.pm.appoitmentservice.appoitmentservice.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByDoctorIdAndDateAndBookedFalse(Long doctorId, String date);
}
