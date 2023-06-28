package hasEntities;

import jakarta.persistence.*;

@Entity
@Table(name = "automobiles")
public class Automobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "plate_number_id", referencedColumnName = "id")
    private PlateNumber plateNumber;

    public Automobile() {
    }

    public Automobile(PlateNumber plateNumber) {
        this.plateNumber = plateNumber;
    }
}
