

package net.pm.appoitmentservice.appoitmentservice.controller;

import net.pm.appoitmentservice.appoitmentservice.dto.DoctorDTO;
import net.pm.appoitmentservice.appoitmentservice.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO dto) {
        DoctorDTO created = doctorService.createDoctor(dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/by-specialty")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@RequestBody SpecialtyRequest request) {
        List<DoctorDTO> doctors = doctorService.getDoctorsBySpecialty(request.getSpecialty());
        return ResponseEntity.ok(doctors);
    }

    // ✅ Simple DTO for filtering by specialty
    public static class SpecialtyRequest {
        private String specialty;

        public String getSpecialty() { return specialty; }
        public void setSpecialty(String specialty) { this.specialty = specialty; }
    }
}



















/*package net.pm.appoitmentservice.appoitmentservice.controller;

import net.pm.appoitmentservice.appoitmentservice.dto.DoctorDTO;
import net.pm.appoitmentservice.appoitmentservice.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping
    public DoctorDTO createDoctor(@RequestBody DoctorDTO dto) {
        return doctorService.createDoctor(dto);
    }

    @PostMapping("/by-specialty")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialty(@RequestBody SpecialtyRequest request) {
        List<DoctorDTO> doctors = doctorService.getDoctorsBySpecialty(request.getSpecialty());
        return ResponseEntity.ok(doctors);
    }

    public static class SpecialtyRequest {
        private String specialty;

        public String getSpecialty() { return specialty; }
        public void setSpecialty(String specialty) { this.specialty = specialty; }
    }
}

 */