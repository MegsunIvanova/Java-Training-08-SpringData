package bg.softuni.productshop_xml.entities.products;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportDTO {

    @XmlElement
    private String name;

    @XmlElement
    private BigDecimal price;

    public ProductImportDTO() {
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
