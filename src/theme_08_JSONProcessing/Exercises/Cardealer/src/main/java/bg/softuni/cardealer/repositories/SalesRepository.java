package bg.softuni.cardealer.repositories;

import bg.softuni.cardealer.entities.Sales.Sale;
import bg.softuni.cardealer.entities.Sales.SaleByCustomersDto;
import bg.softuni.cardealer.entities.Sales.SaleWithDiscountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Integer> {


    @Query("SELECT new bg.softuni.cardealer.entities.Sales.SaleByCustomersDto " +
            "(s.customer.name, COUNT (s.car), SUM(s.priceWithDiscount)) " +
            "FROM Sale s " +
            "GROUP BY s.customer.id ")
    List<SaleByCustomersDto> findAllSalesByCustomers();

    @Query("SELECT new bg.softuni.cardealer.entities.Sales.SaleWithDiscountDto " +
            "(s.car, s.customer.name, s.discount, s.price, s.priceWithDiscount) " +
            "FROM Sale s")
    List<SaleWithDiscountDto> findAllSalesWithDiscountApplied();
}
