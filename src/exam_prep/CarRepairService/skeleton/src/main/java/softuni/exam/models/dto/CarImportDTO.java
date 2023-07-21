package softuni.exam.models.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportDTO {
    //    •	car type – the enumeration, one of the following – SUV, coupe, sport
    //•	car make – accepts char sequence (between 2 to 30 inclusive).
    //•	car model – accepts char sequence (between 2 to 30 inclusive).
    //•	year – accepts a positive number.
    //•	plate number – accepts char sequence (between 2 to 30 inclusive). The values are unique in the database.
    //•	kilometers – accepts a positive number.
    //•	engine – accepts number values that are more than or equal to 1.00.

    @XmlElement
    @NotNull
    @Size(min = 2, max = 30)
    private String carMake;

    @XmlElement
    @NotNull
    @Size(min = 2, max = 30)
    private String carModel;

    @XmlElement
    @NotNull
    @Positive
    private Integer year;

    @XmlElement
    @NotNull
    @Size(min = 2, max = 30)
    private String plateNumber;

    @XmlElement
    @NotNull
    @Positive
    private Integer kilometers;

    @XmlElement
    @NotNull
    @DecimalMin(value = "1.00")
    private Double engine;

    @XmlElement
    @NotNull
    private String carType;

    public CarImportDTO() {
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public Integer getYear() {
        return year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public Double getEngine() {
        return engine;
    }

    public String getCarType() {
        return carType;
    }
}
