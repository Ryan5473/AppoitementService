package net.pm.appoitmentservice.appoitmentservice.dto;

import java.io.Serializable;
import java.util.Objects;

public class SlotDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long doctorId;
    private Long patientId;
    private String doctorName;
    private String date;
    private String startTime;
    private String endTime;
    private boolean booked;
    private String zoomStartUrl;
    private String zoomJoinUrl;
    private int durationMinutes;

    public SlotDTO() {}

    public SlotDTO(Long id, Long doctorId, Long patientId, String doctorName, String date,
                   String startTime, String endTime, boolean booked,
                   String zoomStartUrl, String zoomJoinUrl, int durationMinutes) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.booked = booked;
        this.zoomStartUrl = zoomStartUrl;
        this.zoomJoinUrl = zoomJoinUrl;
        this.durationMinutes = durationMinutes;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isBooked() {
        return booked;
    }
    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getZoomStartUrl() {
        return zoomStartUrl;
    }
    public void setZoomStartUrl(String zoomStartUrl) {
        this.zoomStartUrl = zoomStartUrl;
    }

    public String getZoomJoinUrl() {
        return zoomJoinUrl;
    }
    public void setZoomJoinUrl(String zoomJoinUrl) {
        this.zoomJoinUrl = zoomJoinUrl;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return "SlotDTO{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", doctorName='" + doctorName + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", booked=" + booked +
                ", zoomStartUrl='" + zoomStartUrl + '\'' +
                ", zoomJoinUrl='" + zoomJoinUrl + '\'' +
                ", durationMinutes=" + durationMinutes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SlotDTO)) return false;
        SlotDTO slotDTO = (SlotDTO) o;
        return booked == slotDTO.booked &&
                durationMinutes == slotDTO.durationMinutes &&
                Objects.equals(id, slotDTO.id) &&
                Objects.equals(doctorId, slotDTO.doctorId) &&
                Objects.equals(patientId, slotDTO.patientId) &&
                Objects.equals(doctorName, slotDTO.doctorName) &&
                Objects.equals(date, slotDTO.date) &&
                Objects.equals(startTime, slotDTO.startTime) &&
                Objects.equals(endTime, slotDTO.endTime) &&
                Objects.equals(zoomStartUrl, slotDTO.zoomStartUrl) &&
                Objects.equals(zoomJoinUrl, slotDTO.zoomJoinUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctorId, patientId, doctorName, date, startTime, endTime, booked, zoomStartUrl, zoomJoinUrl, durationMinutes);
    }
}
