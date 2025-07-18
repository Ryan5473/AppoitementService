package net.pm.appoitmentservice.appoitmentservice.dto;

public class SlotDTO {

    private Long id;
    private Long doctorId;
    private Long patientId;
    private String date;
    private String startTime;
    private String endTime;
    private boolean booked;
    private String zoomJoinUrl;
    private String zoomStartUrl;

    public SlotDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public boolean isBooked() { return booked; }
    public void setBooked(boolean booked) { this.booked = booked; }

    public String getZoomJoinUrl() { return zoomJoinUrl; }
    public void setZoomJoinUrl(String zoomJoinUrl) { this.zoomJoinUrl = zoomJoinUrl; }

    public String getZoomStartUrl() { return zoomStartUrl; }
    public void setZoomStartUrl(String zoomStartUrl) { this.zoomStartUrl = zoomStartUrl; }
}
