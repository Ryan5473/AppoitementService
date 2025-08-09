package net.pm.appoitmentservice.appoitmentservice.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long userId; // link to User Service

    private String fullName;
    private String specialty;
    private String contactPhone;
    private String contactEmail;
    private String address;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorWorkingDay> workingDays;

    public Doctor() {}

    public Doctor(Long id, Long userId, String fullName, String specialty,
                  String contactPhone, String contactEmail, String address) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.specialty = specialty;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.address = address;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<DoctorWorkingDay> getWorkingDays() { return workingDays; }
    public void setWorkingDays(List<DoctorWorkingDay> workingDays) { this.workingDays = workingDays; }
}