package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PassengerElementDTO {

    @XmlElement
    private String email;

    public PassengerElementDTO() {
    }

    public String getEmail() {
        return email;
    }
}
