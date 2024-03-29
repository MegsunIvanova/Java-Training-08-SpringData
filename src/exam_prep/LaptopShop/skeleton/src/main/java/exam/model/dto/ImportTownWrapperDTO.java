package exam.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTownWrapperDTO {

    @XmlElement(name = "town")
    private List<ImportTownDTO> towns;

    public ImportTownWrapperDTO() {
    }

    public List<ImportTownDTO> getTowns() {
        return towns;
    }
}
