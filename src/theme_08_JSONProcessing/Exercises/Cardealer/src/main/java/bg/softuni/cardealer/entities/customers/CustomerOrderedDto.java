package bg.softuni.cardealer.entities.customers;

import bg.softuni.cardealer.entities.Sales.SaleOrderedDto;

import java.time.LocalDate;
import java.util.List;

public class CustomerOrderedDto {

    private Integer id;

    private String name;

    private LocalDate birthDate;

    private boolean isYoungDriver;

    private List<SaleOrderedDto> sales;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public List<SaleOrderedDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleOrderedDto> sales) {
        this.sales = sales;
    }
}
