package net.pm.appoitmentservice.appoitmentservice.service;

import net.pm.appoitmentservice.appoitmentservice.dto.DoctorDTO;
import net.pm.appoitmentservice.appoitmentservice.entity.Doctor;
import net.pm.appoitmentservice.appoitmentservice.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO createDoctor(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setUserId(dto.getUserId());
        doctor.setFullName(dto.getFullName());
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setContactPhone(dto.getContactPhone());
        doctor.setContactEmail(dto.getContactEmail());
        doctor.setAddress(dto.getAddress());

        Doctor saved = doctorRepository.save(doctor);
        return toDTO(saved);
    }

    public List<DoctorDTO> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialtyIgnoreCase(specialty)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setUserId(doctor.getUserId());
        dto.setFullName(doctor.getFullName());
        dto.setSpecialty(doctor.getSpecialty());
        dto.setContactPhone(doctor.getContactPhone());
        dto.setContactEmail(doctor.getContactEmail());
        dto.setAddress(doctor.getAddress());
        return dto;
    }
}