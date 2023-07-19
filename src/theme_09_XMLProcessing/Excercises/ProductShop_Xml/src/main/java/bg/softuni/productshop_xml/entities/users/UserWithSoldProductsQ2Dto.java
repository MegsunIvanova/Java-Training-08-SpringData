package bg.softuni.productshop_xml.entities.users;

import bg.softuni.productshop_xml.entities.products.SoldProductQ2DTO;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsQ2Dto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElementWrapper(name = "sold-products")
    @XmlElement(name = "product")
    private List<SoldProductQ2DTO> sellingItems;

    public UserWithSoldProductsQ2Dto() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSellingItems(List<SoldProductQ2DTO> sellingItems) {
        this.sellingItems = sellingItems;
    }
}
