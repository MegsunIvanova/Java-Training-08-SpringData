package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlaneDTO {

    @XmlElement (name = "register-number")
    @Size(min = 5)
    @NotNull
    private String registerNumber;

    @XmlElement
    @Positive
    @NotNull
    private Integer capacity;

    @XmlElement
    @Size(min = 2)
    @NotNull
    private String airline;

    public ImportPlaneDTO() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getAirline() {
        return airline;
    }
}


