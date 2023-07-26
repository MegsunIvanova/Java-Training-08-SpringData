package softuni.exam.models.dto;

import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Town;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTicketDTO {


    @XmlElement(name = "serial-number")
    @Size(min = 2)
    private String serialNumber;

    @XmlElement
    @Positive
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeOff;

    @XmlElement (name = "from-town")
    private TownElementDTO fromTown;

    @XmlElement (name = "to-town")
    private TownElementDTO toTown;

    @XmlElement
    private PassengerElementDTO passenger;

    @XmlElement
    private  PlaneElementDTO plane;

    public ImportTicketDTO() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTakeOff() {
        return takeOff;
    }

    public TownElementDTO getFromTown() {
        return fromTown;
    }

    public TownElementDTO getToTown() {
        return toTown;
    }

    public PassengerElementDTO getPassenger() {
        return passenger;
    }

    public PlaneElementDTO getPlane() {
        return plane;
    }
}
