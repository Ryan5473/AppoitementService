package net.pm.appoitmentservice.appoitmentservice.dto;

public class DoctorWorkingDayDTO {
    private Long id;
    private Long doctorId;
    private String date;
    private String dayOfWeek;
    private String startTime;
    private String endTime;

    public DoctorWorkingDayDTO() {
    }

    public DoctorWorkingDayDTO(Long id, Long doctorId, String date, String dayOfWeek, String startTime, String endTime) {
        this.id = id;
        this.doctorId = doctorId;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
}