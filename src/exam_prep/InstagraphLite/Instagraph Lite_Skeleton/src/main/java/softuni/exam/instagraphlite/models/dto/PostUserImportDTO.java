package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostUserImportDTO {

    @XmlElement
    @NotNull
    private String username;

    public PostUserImportDTO() {
    }

    public String getUsername() {
        return username;
    }
}
