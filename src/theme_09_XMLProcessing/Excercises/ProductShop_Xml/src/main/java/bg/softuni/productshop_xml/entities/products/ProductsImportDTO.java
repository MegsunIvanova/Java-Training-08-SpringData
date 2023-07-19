package bg.softuni.productshop_xml.entities.products;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsImportDTO {

    @XmlElement(name = "product")
    List<ProductImportDTO> products;

    public ProductsImportDTO() {
    }

    public List<ProductImportDTO> getProducts() {
        return products;
    }
}
