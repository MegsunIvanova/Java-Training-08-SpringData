package bg.softuni.productshop_xml.entities.products;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "sold-products")
public class ExportSoldProductsDTO {

    @XmlAttribute
    private long count;
    @XmlElement(name = "product")
    private List<SoldProductQ4DTO> sellingItems;

    public ExportSoldProductsDTO() {
    }

    public ExportSoldProductsDTO(List<SoldProductQ4DTO> sellingItems) {
        this.sellingItems = sellingItems;
        this.count = sellingItems.size();
    }
}
