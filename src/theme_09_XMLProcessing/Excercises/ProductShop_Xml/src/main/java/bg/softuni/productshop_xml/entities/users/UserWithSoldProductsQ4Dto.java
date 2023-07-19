package bg.softuni.productshop_xml.entities.users;

import bg.softuni.productshop_xml.entities.products.ExportSoldProductsDTO;
import bg.softuni.productshop_xml.entities.products.Product;
import bg.softuni.productshop_xml.entities.products.SoldProductQ2DTO;
import bg.softuni.productshop_xml.entities.products.SoldProductQ4DTO;
import jakarta.xml.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsQ4Dto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute
    private Integer age;

    @XmlElement(name = "sold-products")
    private ExportSoldProductsDTO soldProducts;

    public UserWithSoldProductsQ4Dto() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSoldProducts(List<Product> sellingItems) {
        List<SoldProductQ4DTO> productsDto = sellingItems
                .stream()
                .filter(p -> p.getBuyer() != null)
                .map(p -> new SoldProductQ4DTO(p.getName(), p.getPrice()))
                .collect(Collectors.toList());
        this.soldProducts = new ExportSoldProductsDTO(productsDto);
    }
}
