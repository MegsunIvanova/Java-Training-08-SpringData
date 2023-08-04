package bg.softuni.mvcdemo.dtos.companies;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyImportDTO {

    @XmlAttribute (name = "name")
    private String name;

    public String getName() {
        return name;
    }
}
