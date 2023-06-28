package entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

//Table Per Class Strategy:
//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//Table Per Class Joined Strategy:
//@Entity
//@Table(name="vehicles")
//@Inheritance(strategy = InheritanceType.JOINED)

//Single Table Strategy:
@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Vehicle {

    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE) // for Table Per Class Strategy and JOINED Strategy
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Single Table Strategy
    protected long id;

    @Column
    protected String type;

    @Column
    protected String model;

    @Column
    protected BigDecimal price;

    @Column(name = "fuel_type")
    protected String fuelType;

    protected Vehicle() {
    }

    protected Vehicle(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    protected void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
