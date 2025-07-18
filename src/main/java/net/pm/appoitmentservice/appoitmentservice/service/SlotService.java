package net.pm.appoitmentservice.appoitmentservice.service;

import net.pm.appoitmentservice.appoitmentservice.dto.SlotDTO;
import net.pm.appoitmentservice.appoitmentservice.entity.Slot;
import net.pm.appoitmentservice.appoitmentservice.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SlotService {

    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<SlotDTO> getAvailableSlots(Long doctorId, String date) {
        List<Slot> slots = slotRepository.findByDoctorIdAndDateAndBookedFalse(doctorId, date);
        return slots.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public SlotDTO createSlot(Long doctorId, String date, String startTime, String endTime) {
        Slot slot = new Slot();
        slot.setDoctorId(doctorId);
        slot.setDate(date);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setBooked(false);
        slot.setPatientId(null);
        slot.setZoomJoinUrl(null);
        slot.setZoomStartUrl(null);

        slotRepository.save(slot);
        return toDTO(slot);
    }

    public SlotDTO bookSlot(Long slotId, Long patientId) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new IllegalArgumentException("Slot not found with id: " + slotId));

        if (slot.isBooked()) {
            throw new IllegalStateException("Slot is already booked");
        }

        slot.setBooked(true);
        slot.setPatientId(patientId);

        // Generate Zoom meeting
        ZoomHelper.ZoomMeeting meeting = ZoomHelper.createMeeting(slot);
        slot.setZoomJoinUrl(meeting.getJoinUrl());
        slot.setZoomStartUrl(meeting.getStartUrl());

        slotRepository.save(slot);
        return toDTO(slot);
    }

    private SlotDTO toDTO(Slot slot) {
        SlotDTO dto = new SlotDTO();
        dto.setId(slot.getId());
        dto.setDoctorId(slot.getDoctorId());
        dto.setPatientId(slot.getPatientId());
        dto.setDate(slot.getDate());
        dto.setStartTime(slot.getStartTime());
        dto.setEndTime(slot.getEndTime());
        dto.setBooked(slot.isBooked());
        dto.setZoomJoinUrl(slot.getZoomJoinUrl());
        dto.setZoomStartUrl(slot.getZoomStartUrl());
        return dto;
    }
}
