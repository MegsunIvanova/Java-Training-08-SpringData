package bg.softuni.productshop_xml.entities.categories;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesImportDTO {

    @XmlElement(name = "category")
    List<CategoryNameDTO> categories;

    public CategoriesImportDTO() {
    }

    public List<CategoryNameDTO> getCategories() {
        return categories;
    }

}
