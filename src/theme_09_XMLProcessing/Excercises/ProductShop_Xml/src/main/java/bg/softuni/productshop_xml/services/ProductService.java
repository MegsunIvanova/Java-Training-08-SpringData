package bg.softuni.productshop_xml.services;

import bg.softuni.productshop_xml.entities.products.ExportProductsInRangeDTO;

public interface ProductService {

    ExportProductsInRangeDTO getInRange(double from, double to);
}
