package bg.softuni.productshop_xml.entities.users;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportSellersQ2DTO {

    @XmlElement(name = "user")
    private List<UserWithSoldProductsQ2Dto> users;

    public ExportSellersQ2DTO() {
    }
    public ExportSellersQ2DTO(List<UserWithSoldProductsQ2Dto> users) {
        this.users = users;
    }
}
