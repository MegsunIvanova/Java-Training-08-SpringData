package exam.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportShopTownDTO {

    @XmlElement
    private String name;

    public ImportShopTownDTO() {
    }

    public String getName() {
        return name;
    }
}
