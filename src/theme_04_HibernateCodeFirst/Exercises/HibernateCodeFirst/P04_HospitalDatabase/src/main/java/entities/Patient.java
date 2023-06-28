package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
public class Patient extends BaseEntity{

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Column(unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate birthDate;

    @Column
    private String picture;

    @Column(name = "is_medical_insured")
    private boolean isMedicalInsured;

//    @ManyToMany(mappedBy = "patients", targetEntity = Medicament.class)
//    private Set<Medicament> prescribedMed;

    public Patient() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isMedicalInsured() {
        return isMedicalInsured;
    }

    public void setMedicalInsured(boolean medicalInsured) {
        isMedicalInsured = medicalInsured;
    }

//    public Set<Medicament> getPrescribedMed() {
//        return prescribedMed;
//    }
//
//    public void setPrescribedMed(Set<Medicament> prescribedMed) {
//        this.prescribedMed = prescribedMed;
//    }
}
