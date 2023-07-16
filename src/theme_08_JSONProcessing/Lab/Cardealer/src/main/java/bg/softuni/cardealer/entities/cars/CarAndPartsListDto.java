package bg.softuni.cardealer.entities.cars;

import bg.softuni.cardealer.entities.parts.PartDto;

import java.math.BigDecimal;
import java.util.List;

public class CarAndPartsListDto {
    private CarDto car;

    private List<PartDto> parts;

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public List<PartDto> getParts() {
        return parts;
    }

    public void setParts(List<PartDto> parts) {
        this.parts = parts;
    }

}
