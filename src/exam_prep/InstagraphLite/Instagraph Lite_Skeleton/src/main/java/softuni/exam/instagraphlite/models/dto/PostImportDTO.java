package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostImportDTO {

    @XmlElement
    @NotNull
    @Size(min = 21)
    private String caption;

    @XmlElement
    private PostUserImportDTO user;

    @XmlElement
    private PostPictureImportDTO picture;

    public PostImportDTO() {
    }

    public String getCaption() {
        return caption;
    }

    public PostUserImportDTO getUser() {
        return user;
    }

    public PostPictureImportDTO getPicture() {
        return picture;
    }
}
