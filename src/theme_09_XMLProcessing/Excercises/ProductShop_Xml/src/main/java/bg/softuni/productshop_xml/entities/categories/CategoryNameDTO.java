package bg.softuni.productshop_xml.entities.categories;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryNameDTO {

    @XmlElement
    private String name;

    public CategoryNameDTO() {
    }

    public String getName() {
        return name;
    }
}
