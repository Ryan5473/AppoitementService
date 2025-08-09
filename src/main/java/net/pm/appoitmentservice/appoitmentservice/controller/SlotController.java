

package net.pm.appoitmentservice.appoitmentservice.controller;

import net.pm.appoitmentservice.appoitmentservice.dto.SlotDTO;
import net.pm.appoitmentservice.appoitmentservice.dto.request.BookSlotRequest;
import net.pm.appoitmentservice.appoitmentservice.dto.request.CreateSlotRequest;
import net.pm.appoitmentservice.appoitmentservice.service.SlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("/available")
    public ResponseEntity<List<SlotDTO>> getAvailableSlots(@RequestParam Long doctorId, @RequestParam String date) {
        List<SlotDTO> slots = slotService.getAvailableSlots(doctorId, date);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/book")
    public ResponseEntity<SlotDTO> bookSlot(@RequestBody BookSlotRequest request) {
        SlotDTO booked = slotService.bookSlot(request);
        return ResponseEntity.ok(booked);
    }

    @GetMapping("/booked/patient/{patientId}")
    public ResponseEntity<List<SlotDTO>> getBookedSlotsForPatient(@PathVariable Long patientId) {
        List<SlotDTO> slots = slotService.getBookedSlotsForPatient(patientId);
        return ResponseEntity.ok(slots);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<SlotDTO>> getAllSlotsByDoctor(@PathVariable Long doctorId) {
        List<SlotDTO> slots = slotService.getAllSlotsByDoctor(doctorId);
        return ResponseEntity.ok(slots);
    }
    @GetMapping("/working-day")
    public ResponseEntity<List<SlotDTO>> getSlotsByWorkingDayAndDoctor(
            @RequestParam Long workingDayId,
            @RequestParam Long doctorId) {
        List<SlotDTO> slots = slotService.getSlotsByWorkingDayAndDoctor(workingDayId, doctorId);
        return ResponseEntity.ok(slots);
    }



    @PostMapping("/create")
    public ResponseEntity<SlotDTO> createSlot(@RequestBody CreateSlotRequest request) {
        SlotDTO created = slotService.createSlot(request);
        return ResponseEntity.ok(created);
    }
}





























/*

package net.pm.appoitmentservice.appoitmentservice.controller;

import net.pm.appoitmentservice.appoitmentservice.dto.SlotDTO;
import net.pm.appoitmentservice.appoitmentservice.dto.request.BookSlotRequest;
import net.pm.appoitmentservice.appoitmentservice.dto.request.CreateSlotRequest;
import net.pm.appoitmentservice.appoitmentservice.service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("/available")
    public List<SlotDTO> getAvailableSlots(@RequestParam Long doctorId, @RequestParam String date) {
        return slotService.getAvailableSlots(doctorId, date);
    }

    @PostMapping("/book")
    public SlotDTO bookSlot(@RequestBody BookSlotRequest request) {
        return slotService.bookSlot(request);
    }

    @GetMapping("/booked/patient/{patientId}")
    public List<SlotDTO> getBookedSlotsForPatient(@PathVariable Long patientId) {
        return slotService.getBookedSlotsForPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<SlotDTO> getAllSlotsByDoctor(@PathVariable Long doctorId) {
        return slotService.getAllSlotsByDoctor(doctorId);
    }

    @PostMapping("/create")
    public SlotDTO createSlot(@RequestBody CreateSlotRequest request) {
        return slotService.createSlot(request);
    }
}

 */
