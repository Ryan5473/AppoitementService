package net.pm.appoitmentservice.appoitmentservice.entity;

import jakarta.persistence.*;

@Entity
public class Slot  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    private String date; // yyyy-MM-dd
    private String startTime;
    private String endTime;

    private boolean booked;

    @Column(columnDefinition = "TEXT")
    private String zoomStartUrl;

    @Column(columnDefinition = "TEXT")
    private String zoomJoinUrl;

    @ManyToOne
    @JoinColumn(name = "working_day_id")
    private DoctorWorkingDay workingDay;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public String getZoomStartUrl() { return zoomStartUrl; }
    public void setZoomStartUrl(String zoomStartUrl) { this.zoomStartUrl = zoomStartUrl; }

    public String getZoomJoinUrl() { return zoomJoinUrl; }
    public void setZoomJoinUrl(String zoomJoinUrl) { this.zoomJoinUrl = zoomJoinUrl; }

    public DoctorWorkingDay getWorkingDay() { return workingDay; }
    public void setWorkingDay(DoctorWorkingDay workingDay) { this.workingDay = workingDay; }
}
