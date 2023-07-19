package bg.softuni.productshop_xml.entities.users;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersImportDTO {

    @XmlElement(name = "user")
    private List<UserImportDTO> users;

    public UsersImportDTO() {
    }

    public List<UserImportDTO> getUsers() {
        return users;
    }
}
