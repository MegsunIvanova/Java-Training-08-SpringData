package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Medicament extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "medicaments_patients",
            joinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
    private Set<Patient> patients;

    public Medicament() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }


}
