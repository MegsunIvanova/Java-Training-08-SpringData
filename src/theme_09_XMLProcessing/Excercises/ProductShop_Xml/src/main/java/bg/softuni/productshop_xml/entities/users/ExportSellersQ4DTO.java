package bg.softuni.productshop_xml.entities.users;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportSellersQ4DTO {

    @XmlAttribute
    private long count;

    @XmlElement (name = "user")
    private List<UserWithSoldProductsQ4Dto> users;

    public ExportSellersQ4DTO() {
    }

    public ExportSellersQ4DTO(List<UserWithSoldProductsQ4Dto> users) {
        this.users = users;
        this.count = users.size();
    }

}
