package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class TaskImportDTO {

    //•	price – accepts a very big positive number.
    //•	date – a date and time of registering the task in the "yyyy-MM-dd HH:mm:ss" format.

    @XmlElement
    @NotNull
    private String date;

    @XmlElement
    @NotNull
    @Positive
    private BigDecimal price;

    @XmlElement
    @NotNull
    private CarBaseDTO car;

    @XmlElement
    @NotNull
    private MechanicBaseDTO mechanic;

    @XmlElement
    @NotNull
    private PartBaseDTO part;


    public TaskImportDTO() {
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CarBaseDTO getCar() {
        return car;
    }

    public MechanicBaseDTO getMechanic() {
        return mechanic;
    }

    public PartBaseDTO getPart() {
        return part;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCar(CarBaseDTO car) {
        this.car = car;
    }

    public void setMechanic(MechanicBaseDTO mechanic) {
        this.mechanic = mechanic;
    }

    public void setPart(PartBaseDTO part) {
        this.part = part;
    }
}
