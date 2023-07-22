package softuni.exam.models.dto;

import softuni.exam.models.entity.ApartmentType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentDTO {

    @XmlElement
    private String apartmentType;

    @XmlElement
    private Double area;

    @XmlElement(name = "town")
    private String townName;

    public ImportApartmentDTO() {
    }

    public String getApartmentType() {
        return apartmentType;
    }

    public Double getArea() {
        return area;
    }

    public String getTownName() {
        return townName;
    }
}
