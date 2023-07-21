package softuni.exam.models.dto;

import java.math.BigDecimal;

public class TaskDTO {
    private Long id;
    private BigDecimal price;
    private MechanicBasicInfoDTO mechanic;

    private CarBasicInfoDTO car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MechanicBasicInfoDTO getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicBasicInfoDTO mechanic) {
        this.mechanic = mechanic;
    }

    public CarBasicInfoDTO getCar() {
        return car;
    }

    public void setCar(CarBasicInfoDTO car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return String.format("Car %s %s with %dkm%n" +
                        "-Mechanic: %s %s - task №%d:%n" +
                        " --Engine: %.1f%n" +
                        "---Price: %s$",
                this.car.getCarMake(),
                this.car.getCarModel(),
                this.car.getKilometers(),
                this.mechanic.getFirstName(),
                this.mechanic.getLastName(),
                this.id,
                this.car.getEngine(),
                this.price.setScale(2));
    }
}
