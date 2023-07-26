package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneElementDTO {

    @XmlElement(name = "register-number")
    private String registerNumber;

    public PlaneElementDTO() {
    }

    public String getRegisterNumber() {
        return registerNumber;
    }
}
