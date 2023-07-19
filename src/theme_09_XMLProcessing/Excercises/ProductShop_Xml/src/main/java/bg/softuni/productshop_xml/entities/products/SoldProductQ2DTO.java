package bg.softuni.productshop_xml.entities.products;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;

@XmlRootElement (name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductQ2DTO {

    @XmlElement
    private String name;
    @XmlElement
    private BigDecimal price;

    @XmlElement (name = "buyer-first-name")
    private String buyerFirstName;

    @XmlElement (name = "buyer-last-name")
    private String buyerLastName;

    public SoldProductQ2DTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setBuyerFirstName(String buyerFirstName) {
        this.buyerFirstName = buyerFirstName;
    }

    public void setBuyerLastName(String buyerLastName) {
        this.buyerLastName = buyerLastName;
    }
}
