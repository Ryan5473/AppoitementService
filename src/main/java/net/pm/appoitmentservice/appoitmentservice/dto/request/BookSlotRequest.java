package net.pm.appoitmentservice.appoitmentservice.dto.request;

public class BookSlotRequest{

    private Long slotId;
    private Long patientId;

    public BookSlotRequest() {
    }

    public BookSlotRequest(Long slotId, Long patientId) {
        this.slotId = slotId;
        this.patientId = patientId;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}