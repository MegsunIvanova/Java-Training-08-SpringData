package bg.softuni.productshop_xml.entities.products;

import jakarta.xml.bind.annotation.*;

import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductQ4DTO {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private BigDecimal price;

    public SoldProductQ4DTO() {
    }

    public SoldProductQ4DTO(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
