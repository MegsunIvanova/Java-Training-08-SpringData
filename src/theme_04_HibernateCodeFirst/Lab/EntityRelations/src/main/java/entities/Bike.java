package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
//@Table(name = "bikes") // for Table Per Class Strategy and JOINED Strategy
public class Bike extends Vehicle {

    private static final String BIKE_TYPE = "BIKE";

    public Bike() {
        super(BIKE_TYPE);
    }
}
