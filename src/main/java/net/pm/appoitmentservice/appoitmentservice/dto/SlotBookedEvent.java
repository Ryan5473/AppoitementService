package net.pm.appoitmentservice.appoitmentservice.dto;

public class SlotBookedEvent  {
    private Long slotId;
    private Long doctorId;
    private Long patientId;
    private String date;
    private String startTime;
    private String zoomJoinUrl;

    public SlotBookedEvent() {
    }

    public SlotBookedEvent(Long slotId, Long doctorId, Long patientId, String date, String startTime, String zoomJoinUrl) {
        this.slotId = slotId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.startTime = startTime;
        this.zoomJoinUrl = zoomJoinUrl;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getZoomJoinUrl() {
        return zoomJoinUrl;
    }

    public void setZoomJoinUrl(String zoomJoinUrl) {
        this.zoomJoinUrl = zoomJoinUrl;
    }
}