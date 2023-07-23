package exam.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportTownDTO {

    @XmlElement
    @NotNull
    @Size(min = 2)
    private String name;

    @XmlElement
    @NotNull
    @Positive
    private Integer population;


    @XmlElement(name = "travel-guide")
    @NotNull
    @Size(min = 10)
    private String travelGuide;

    public ImportTownDTO() {
    }

    public String getName() {
        return name;
    }

    public Integer getPopulation() {
        return population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }
}
