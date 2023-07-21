package bg.softuni.cardealer;

import bg.softuni.cardealer.entities.Sales.SaleByCustomersDto;
import bg.softuni.cardealer.entities.Sales.SaleWithDiscountDto;
import bg.softuni.cardealer.entities.cars.CarAndPartsListDto;
import bg.softuni.cardealer.entities.cars.CarsToyotaDto;
import bg.softuni.cardealer.entities.customers.CustomerOrderedDto;
import bg.softuni.cardealer.entities.suppliers.LocalSupplierDto;
import bg.softuni.cardealer.services.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarDealerRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final CustomersService customersService;
    private final CarsService carsService;
    private final SuppliersService suppliersService;
    private final SalesService salesService;
    private final Gson gson;

    @Autowired
    public CarDealerRunner(SeedService seedService, CustomersService customersService,
                           CarsService carsService, SuppliersService suppliersService,
                           SalesService salesService, Gson gson) {
        this.seedService = seedService;
        this.customersService = customersService;
        this.carsService = carsService;
        this.suppliersService = suppliersService;
        this.salesService = salesService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAll();

//        q01_getAllCustomersOrderedByBirthdate();

//        q02_getAllToyotaCarsOrderedByModelAndDistance();

//        q03_getLocalSuppliersWithPartsCount();

//        q04_getAllCarsWithPartsList();

//        q05_getTotalSalesByCustomers();

        q06_getSalesWithDiscount();


    }

    private void q06_getSalesWithDiscount() {
        List<SaleWithDiscountDto> saleWithDiscountDTOs = this.salesService.getAllSalesWithDiscountApplied();

        String json = gson.toJson(saleWithDiscountDTOs);

        System.out.println(json);
    }

    private void q05_getTotalSalesByCustomers() {
        List<SaleByCustomersDto> saleByCustomersDTOs = this.salesService.getSalesByCustomers();

        String json = gson.toJson(saleByCustomersDTOs);

        System.out.println(json);
    }

    private void q04_getAllCarsWithPartsList() {
        List<CarAndPartsListDto> carAndPartsListDTOs = this.carsService.getAllCarsWithPartsList();

        String json = gson.toJson(carAndPartsListDTOs);

        System.out.println(json);
    }

    private void q03_getLocalSuppliersWithPartsCount() {
        List<LocalSupplierDto> localSupplierDTOs =
                this.suppliersService.getLocalSuppliersWithPartsCount();

        String json = gson.toJson(localSupplierDTOs);

        System.out.println(json);

    }

    private void q02_getAllToyotaCarsOrderedByModelAndDistance() {
        List<CarsToyotaDto> cars = this.carsService.getAllToyotaCarsOrderedByModelAndDistance();

        String json = gson.toJson(cars);

        System.out.println(json);
    }

    private void q01_getAllCustomersOrderedByBirthdate() {
        List<CustomerOrderedDto> customers = this.customersService.getAllCustomersOrderedByBirthdate();

        String json = this.gson.toJson(customers);

        System.out.println(json);
    }

}
