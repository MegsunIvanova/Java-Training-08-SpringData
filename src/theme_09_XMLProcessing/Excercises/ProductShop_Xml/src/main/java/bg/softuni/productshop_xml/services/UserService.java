package bg.softuni.productshop_xml.services;

import bg.softuni.productshop_xml.entities.users.ExportSellersQ2DTO;
import bg.softuni.productshop_xml.entities.users.ExportSellersQ4DTO;

public interface UserService {
    ExportSellersQ2DTO findAllWithSoldProductsAndBuyers();

    ExportSellersQ4DTO findAllWithSoldProducts();
}
