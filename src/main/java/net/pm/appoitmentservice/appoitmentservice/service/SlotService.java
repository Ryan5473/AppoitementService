package net.pm.appoitmentservice.appoitmentservice.service;

import net.pm.appoitmentservice.appoitmentservice.dto.SlotDTO;
import net.pm.appoitmentservice.appoitmentservice.dto.request.BookSlotRequest;
import net.pm.appoitmentservice.appoitmentservice.dto.request.CreateSlotRequest;
import net.pm.appoitmentservice.appoitmentservice.entity.DoctorWorkingDay;
import net.pm.appoitmentservice.appoitmentservice.entity.Slot;
import net.pm.appoitmentservice.appoitmentservice.kafka.SlotBookedProducer;
import net.pm.appoitmentservice.appoitmentservice.repository.DoctorWorkingDayRepository;
import net.pm.appoitmentservice.appoitmentservice.repository.SlotRepository;
import net.pm.appoitmentservice.appoitmentservice.utility.ZoomHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SlotService {

    private final SlotRepository slotRepository;
    private final DoctorWorkingDayRepository workingDayRepository;
    private final SlotBookedProducer slotBookedProducer;
    private final ZoomHelper zoomHelper;

    public SlotService(SlotRepository slotRepository,
                       DoctorWorkingDayRepository workingDayRepository,
                       SlotBookedProducer slotBookedProducer,
                       ZoomHelper zoomHelper) {
        this.slotRepository = slotRepository;
        this.workingDayRepository = workingDayRepository;
        this.slotBookedProducer = slotBookedProducer;
        this.zoomHelper = zoomHelper;
    }

    @Transactional
    public SlotDTO bookSlot(BookSlotRequest request) {
        Long slotId = request.getSlotId();
        Long patientId = request.getPatientId();

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found with id " + slotId));

        if (slot.isBooked()) {
            throw new RuntimeException("Slot already booked");
        }

        slot.setBooked(true);
        slot.setPatientId(patientId);

        // Create Zoom meeting and get URLs
        ZoomHelper.ZoomMeetingUrls zoomUrls = zoomHelper.createZoomMeeting(slot);
        slot.setZoomStartUrl(zoomUrls.startUrl);
        slot.setZoomJoinUrl(zoomUrls.joinUrl);

        slotRepository.save(slot);

        // slotBookedProducer.send(...) // optional Kafka event

        return toDTO(slot);
    }

    public SlotDTO createSlot(CreateSlotRequest request) {
        Long workingDayId = request.getWorkingDayId();
        String date = request.getDate();
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        DoctorWorkingDay workingDay = workingDayRepository.findById(workingDayId)
                .orElseThrow(() -> new RuntimeException("Working day not found with id " + workingDayId));

        LocalTime localStart = LocalTime.parse(startTime);
        LocalTime localEnd = LocalTime.parse(endTime);

        if (!localEnd.isAfter(localStart)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Slot slot = new Slot();
        slot.setDate(date);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setBooked(false);
        slot.setWorkingDay(workingDay);

        return toDTO(slotRepository.save(slot));
    }

    public List<SlotDTO> getSlotsByDoctorAndDate(Long doctorId, String date) {
        return slotRepository.findByWorkingDay_Doctor_IdAndDate(doctorId, date)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SlotDTO> getAvailableSlots(Long doctorId, String date) {
        return slotRepository.findByWorkingDay_Doctor_IdAndDateAndBookedFalse(doctorId, date)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SlotDTO> getBookedSlotsForPatient(Long patientId) {
        return slotRepository.findByPatientId(patientId).stream()
                .filter(Slot::isBooked)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public List<SlotDTO> getSlotsByWorkingDayAndDoctor(Long workingDayId, Long doctorId) {
        return slotRepository.findByWorkingDay_IdAndWorkingDay_Doctor_Id(workingDayId, doctorId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SlotDTO> getAllSlotsByDoctor(Long doctorId) {
        return slotRepository.findByWorkingDay_Doctor_Id(doctorId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private SlotDTO toDTO(Slot slot) {
        SlotDTO dto = new SlotDTO();
        dto.setId(slot.getId());
        dto.setDate(slot.getDate());
        dto.setStartTime(slot.getStartTime());
        dto.setEndTime(slot.getEndTime());
        dto.setBooked(slot.isBooked());
        dto.setPatientId(slot.getPatientId());
        dto.setZoomStartUrl(slot.getZoomStartUrl());
        dto.setZoomJoinUrl(slot.getZoomJoinUrl());

        if (slot.getWorkingDay() != null && slot.getWorkingDay().getDoctor() != null) {
            dto.setDoctorId(slot.getWorkingDay().getDoctor().getId());
            dto.setDoctorName(slot.getWorkingDay().getDoctor().getFullName());
        }

        return dto;
    }
}






















/*









package net.pm.appoitmentservice.appoitmentservice.service;

import net.pm.appoitmentservice.appoitmentservice.dto.SlotDTO;
import net.pm.appoitmentservice.appoitmentservice.dto.request.BookSlotRequest;
import net.pm.appoitmentservice.appoitmentservice.dto.request.CreateSlotRequest;
import net.pm.appoitmentservice.appoitmentservice.entity.DoctorWorkingDay;
import net.pm.appoitmentservice.appoitmentservice.entity.Slot;
import net.pm.appoitmentservice.appoitmentservice.kafka.SlotBookedProducer;
import net.pm.appoitmentservice.appoitmentservice.repository.DoctorRepository;
import net.pm.appoitmentservice.appoitmentservice.repository.DoctorWorkingDayRepository;
import net.pm.appoitmentservice.appoitmentservice.repository.SlotRepository;
import net.pm.appoitmentservice.appoitmentservice.utility.ZoomHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SlotService {

    private final SlotRepository slotRepository;
    private final DoctorWorkingDayRepository workingDayRepository;
    private final DoctorRepository doctorRepository;
    private final SlotBookedProducer slotBookedProducer;
    private final ZoomHelper zoomHelper;

    public SlotService(SlotRepository slotRepository,
                       DoctorWorkingDayRepository workingDayRepository,
                       DoctorRepository doctorRepository,
                       SlotBookedProducer slotBookedProducer,
                       ZoomHelper zoomHelper) {
        this.slotRepository = slotRepository;
        this.workingDayRepository = workingDayRepository;
        this.doctorRepository = doctorRepository;
        this.slotBookedProducer = slotBookedProducer;
        this.zoomHelper = zoomHelper;
    }

    @Transactional
    public SlotDTO bookSlot(BookSlotRequest request) {
        Long slotId = request.getSlotId();
        Long patientId = request.getPatientId();

        Optional<Slot> slotOpt = slotRepository.findById(slotId);
        if (slotOpt.isEmpty()) {
            throw new RuntimeException("Slot not found with id " + slotId);
        }
        Slot slot = slotOpt.get();
        if (slot.isBooked()) {
            throw new RuntimeException("Slot already booked");
        }

        slot.setBooked(true);
        slot.setPatientId(patientId);

        // Create Zoom meeting and get URLs
        ZoomHelper.ZoomMeetingUrls zoomUrls = zoomHelper.createZoomMeeting(slot);
        slot.setZoomStartUrl(zoomUrls.startUrl);
        slot.setZoomJoinUrl(zoomUrls.joinUrl);

        slotRepository.save(slot);

        // Optional: send Kafka event
        // slotBookedProducer.send(...);

        return toDTO(slot);
    }

    public SlotDTO createSlot(CreateSlotRequest request) {
        Long doctorId = request.getDoctorId();
        String date = request.getDate();
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        LocalDate localDate = LocalDate.parse(date);
        LocalTime localStart = LocalTime.parse(startTime);
        LocalTime localEnd = LocalTime.parse(endTime);

        if (!localEnd.isAfter(localStart)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        DoctorWorkingDay workingDay = workingDayRepository.findByDoctor_IdAndDate(doctorId, localDate)
                .stream().findFirst()
                .orElseGet(() -> {
                    DoctorWorkingDay wd = new DoctorWorkingDay();
                    wd.setDoctor(doctorRepository.findById(doctorId).orElseThrow(() ->
                            new RuntimeException("Doctor not found with id " + doctorId)));
                    wd.setDate(localDate);
                    wd.setDayOfWeek(localDate.getDayOfWeek());
                    wd.setStartTime(LocalTime.of(9, 0));
                    wd.setEndTime(LocalTime.of(17, 0));
                    return workingDayRepository.save(wd);
                });

        Slot slot = new Slot();
        slot.setDoctorId(doctorId);
        slot.setDate(date);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slot.setBooked(false);
        slot.setWorkingDay(workingDay);

        return toDTO(slotRepository.save(slot));
    }

    public List<SlotDTO> getSlotsByDoctorAndDate(Long doctorId, String date) {
        return slotRepository.findByDoctorIdAndDate(doctorId, date).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SlotDTO> getAvailableSlots(Long doctorId, String date) {
        return slotRepository.findByDoctorIdAndDateAndBookedFalse(doctorId, date).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SlotDTO> getBookedSlotsForPatient(Long patientId) {
        return slotRepository.findByPatientId(patientId).stream()
                .filter(Slot::isBooked)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SlotDTO> getAllSlotsByDoctor(Long doctorId) {
        return slotRepository.findByDoctorId(doctorId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
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
*/