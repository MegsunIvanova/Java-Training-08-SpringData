package bg.softuni.productshop_xml.entities.categories;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportCategoriesWithProductsDTO {


    @XmlElement(name = "category")
    List<CategoryWithProductsDTO> categories;

    public ExportCategoriesWithProductsDTO() {
    }

    public ExportCategoriesWithProductsDTO(List<CategoryWithProductsDTO> categories) {
        this.categories = categories;
    }

  }
