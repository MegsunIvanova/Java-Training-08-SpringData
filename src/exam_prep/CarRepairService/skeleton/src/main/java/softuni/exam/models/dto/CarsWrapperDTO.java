package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsWrapperDTO {

    @XmlElement(name = "car")
    private List<CarImportDTO> cars;

    public CarsWrapperDTO() {
    }

    public List<CarImportDTO> getCars() {
        return cars;
    }
}
