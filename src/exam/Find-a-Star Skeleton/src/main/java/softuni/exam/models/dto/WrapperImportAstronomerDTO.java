package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class WrapperImportAstronomerDTO {

    @XmlElement(name = "astronomer")
    private List<ImportAstronomerDTO> astronomers;

    public WrapperImportAstronomerDTO() {
    }

    public List<ImportAstronomerDTO> getAstronomers() {
        return astronomers;
    }

    public void setAstronomers(List<ImportAstronomerDTO> astronomers) {
        this.astronomers = astronomers;
    }
}
