package net.pm.appoitmentservice.appoitmentservice.dto.request;

public class CreateSlotRequest{

    private Long workingDayId; // 👈 Correct: you link Slot to DoctorWorkingDay
    private String date;       // yyyy-MM-dd
    private String startTime;  // HH:mm
    private String endTime;    // HH:mm

    public CreateSlotRequest() {
    }

    public CreateSlotRequest(Long workingDayId, String date, String startTime, String endTime) {
        this.workingDayId = workingDayId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getWorkingDayId() {
        return workingDayId;
    }

    public void setWorkingDayId(Long workingDayId) {
        this.workingDayId = workingDayId;
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
}