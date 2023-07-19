package bg.softuni.productshop_xml.services;

import bg.softuni.productshop_xml.entities.categories.ExportCategoriesWithProductsDTO;

public interface CategoryService {
    ExportCategoriesWithProductsDTO findCategoriesWithNumberOfProducts();
}
