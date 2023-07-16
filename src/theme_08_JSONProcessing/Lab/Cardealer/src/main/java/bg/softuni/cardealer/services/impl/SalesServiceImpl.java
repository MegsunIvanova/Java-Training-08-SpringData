package bg.softuni.cardealer.services.impl;

import bg.softuni.cardealer.entities.Sales.Sale;
import bg.softuni.cardealer.entities.Sales.SaleByCustomersDto;
import bg.softuni.cardealer.entities.Sales.SaleWithDiscountDto;
import bg.softuni.cardealer.repositories.SalesRepository;
import bg.softuni.cardealer.services.SalesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;

    public SalesServiceImpl(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }


    @Override
    @Transactional
    public List<SaleByCustomersDto> getSalesByCustomers() {
        return this.salesRepository.findAllSalesByCustomers();
    }

    @Override
    @Transactional
    public List<SaleWithDiscountDto> getAllSalesWithDiscountApplied() {
        return this.salesRepository.findAllSalesWithDiscountApplied();
    }
}
