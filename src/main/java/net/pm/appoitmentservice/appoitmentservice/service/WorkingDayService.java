
package net.pm.appoitmentservice.appoitmentservice.service;

import net.pm.appoitmentservice.appoitmentservice.dto.DoctorWorkingDayDTO;
import net.pm.appoitmentservice.appoitmentservice.entity.Doctor;
import net.pm.appoitmentservice.appoitmentservice.entity.DoctorWorkingDay;
import net.pm.appoitmentservice.appoitmentservice.repository.DoctorRepository;
import net.pm.appoitmentservice.appoitmentservice.repository.DoctorWorkingDayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkingDayService {

    private final DoctorWorkingDayRepository workingDayRepository;
    private final DoctorRepository doctorRepository;

    public WorkingDayService(DoctorWorkingDayRepository workingDayRepository, DoctorRepository doctorRepository) {
        this.workingDayRepository = workingDayRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorWorkingDayDTO> getWorkingDaysByDoctor(Long doctorId) {
        return workingDayRepository.findByDoctor_Id(doctorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void generate7WorkingDaysForDoctor(Long doctorId) {
        LocalDate today = LocalDate.now();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id " + doctorId));

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            boolean exists = !workingDayRepository.findByDoctor_IdAndDate(doctorId, date).isEmpty();
            if (!exists) {
                DoctorWorkingDay wd = new DoctorWorkingDay();
                wd.setDoctor(doctor);
                wd.setDate(date);
                wd.setDayOfWeek(date.getDayOfWeek());
                wd.setStartTime(LocalTime.of(9, 0));
                wd.setEndTime(LocalTime.of(17, 0));
                workingDayRepository.save(wd);
            }
        }
    }

    private DoctorWorkingDayDTO toDTO(DoctorWorkingDay wd) {
        DoctorWorkingDayDTO dto = new DoctorWorkingDayDTO();
        dto.setId(wd.getId());
        dto.setDoctorId(wd.getDoctor().getId());
        dto.setDate(wd.getDate().toString());
        dto.setDayOfWeek(wd.getDayOfWeek().name());
        dto.setStartTime(wd.getStartTime().toString());
        dto.setEndTime(wd.getEndTime().toString());
        return dto;
    }
}













/*
package net.pm.appoitmentservice.appoitmentservice.service;

import net.pm.appoitmentservice.appoitmentservice.dto.DoctorWorkingDayDTO;
import net.pm.appoitmentservice.appoitmentservice.entity.Doctor;
import net.pm.appoitmentservice.appoitmentservice.entity.DoctorWorkingDay;
import net.pm.appoitmentservice.appoitmentservice.repository.DoctorRepository;
import net.pm.appoitmentservice.appoitmentservice.repository.DoctorWorkingDayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkingDayService {

    private final DoctorWorkingDayRepository workingDayRepository;
    private final DoctorRepository doctorRepository;

    public WorkingDayService(DoctorWorkingDayRepository workingDayRepository, DoctorRepository doctorRepository) {
        this.workingDayRepository = workingDayRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorWorkingDayDTO> getWorkingDaysByDoctor(Long doctorId) {
        return workingDayRepository.findByDoctor_Id(doctorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void generate7WorkingDaysForDoctor(Long doctorId) {
        LocalDate today = LocalDate.now();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            boolean exists = !workingDayRepository.findByDoctor_IdAndDate(doctorId, date).isEmpty();
            if (!exists) {
                DoctorWorkingDay wd = new DoctorWorkingDay();
                wd.setDoctor(doctor);
                wd.setDate(date);
                wd.setDayOfWeek(date.getDayOfWeek());
                wd.setStartTime(LocalTime.of(9, 0));
                wd.setEndTime(LocalTime.of(17, 0));
                workingDayRepository.save(wd);
            }
        }
    }

    private DoctorWorkingDayDTO toDTO(DoctorWorkingDay wd) {
        DoctorWorkingDayDTO dto = new DoctorWorkingDayDTO();
        dto.setId(wd.getId());
        dto.setDoctorId(wd.getDoctor().getId());
        dto.setDate(wd.getDate().toString());
        dto.setDayOfWeek(wd.getDayOfWeek().name());
        dto.setStartTime(wd.getStartTime().toString());
        dto.setEndTime(wd.getEndTime().toString());
        return dto;
    }
}
*/