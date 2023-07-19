package bg.softuni.productshop_xml.entities.products;

import jakarta.xml.bind.annotation.*;

import java.math.BigDecimal;

@XmlRootElement (name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductWithAttributesDTO {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private BigDecimal price;

    @XmlAttribute
    private String seller;

    public ProductWithAttributesDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
