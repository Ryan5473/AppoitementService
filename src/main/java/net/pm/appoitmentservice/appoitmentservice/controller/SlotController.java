package net.pm.appoitmentservice.appoitmentservice.controller;

import net.pm.appoitmentservice.appoitmentservice.dto.SlotDTO;
import net.pm.appoitmentservice.appoitmentservice.service.SlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/by-date")
    public ResponseEntity<List<SlotDTO>> getAvailableSlots(@RequestBody SlotRequest request) {
        List<SlotDTO> slots = slotService.getAvailableSlots(request.getDoctorId(), request.getDate());
        return ResponseEntity.ok(slots);
    }

    @PutMapping("/book")
    public ResponseEntity<SlotDTO> bookSlot(@RequestBody BookSlotRequest request) {
        SlotDTO bookedSlot = slotService.bookSlot(request.getSlotId(), request.getPatientId());
        return ResponseEntity.ok(bookedSlot);
    }

    @PostMapping("/create")
    public ResponseEntity<SlotDTO> createSlot(@RequestBody CreateSlotRequest request) {
        SlotDTO createdSlot = slotService.createSlot(request.getDoctorId(), request.getDate(), request.getStartTime(), request.getEndTime());
        return ResponseEntity.ok(createdSlot);
    }

    // DTOs for request bodies

    public static class SlotRequest {
        private Long doctorId;
        private String date;

        public Long getDoctorId() { return doctorId; }
        public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
    }

    public static class BookSlotRequest {
        private Long slotId;
        private Long patientId;

        public Long getSlotId() { return slotId; }
        public void setSlotId(Long slotId) { this.slotId = slotId; }

        public Long getPatientId() { return patientId; }
        public void setPatientId(Long patientId) { this.patientId = patientId; }
    }

    public static class CreateSlotRequest {
        private Long doctorId;
        private String date;
        private String startTime;
        private String endTime;

        public Long getDoctorId() { return doctorId; }
        public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }

        public String getEndTime() { return endTime; }
        public void setEndTime(String endTime) { this.endTime = endTime; }
    }
}
