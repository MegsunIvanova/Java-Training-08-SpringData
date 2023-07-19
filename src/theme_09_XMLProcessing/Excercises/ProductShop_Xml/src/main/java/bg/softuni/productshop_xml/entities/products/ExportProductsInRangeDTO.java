package bg.softuni.productshop_xml.entities.products;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportProductsInRangeDTO {

    @XmlElement(name = "product")
    private List<ProductWithAttributesDTO> products;

    public ExportProductsInRangeDTO() {
    }

    public ExportProductsInRangeDTO(List<ProductWithAttributesDTO> products) {
        this.products = products;
    }
}
