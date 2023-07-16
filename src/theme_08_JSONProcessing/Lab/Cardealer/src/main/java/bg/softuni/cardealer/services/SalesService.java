package bg.softuni.cardealer.services;

import bg.softuni.cardealer.entities.Sales.SaleByCustomersDto;
import bg.softuni.cardealer.entities.Sales.SaleWithDiscountDto;

import java.util.List;

public interface SalesService {
    List<SaleByCustomersDto> getSalesByCustomers();

    List<SaleWithDiscountDto> getAllSalesWithDiscountApplied();
}
