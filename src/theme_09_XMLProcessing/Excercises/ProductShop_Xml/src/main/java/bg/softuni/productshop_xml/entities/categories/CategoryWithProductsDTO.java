package bg.softuni.productshop_xml.entities.categories;

import jakarta.xml.bind.annotation.*;

import java.math.BigDecimal;

@XmlRootElement (name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryWithProductsDTO {

    @XmlAttribute
    private String name;

    @XmlElement(name = "products-count")
    private int productsCount;

    @XmlElement(name = "average-price")
    private double averagePrice;

    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;

    public CategoryWithProductsDTO() {
    }

    public CategoryWithProductsDTO(String name, int productsCount, double averagePrice, BigDecimal totalRevenue) {
        this.name = name;
        this.productsCount = productsCount;
        this.averagePrice = averagePrice;
        this.totalRevenue = totalRevenue;
    }
}


