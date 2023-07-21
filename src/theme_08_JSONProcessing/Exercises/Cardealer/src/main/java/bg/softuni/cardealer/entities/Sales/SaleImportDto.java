package bg.softuni.cardealer.entities.Sales;

import bg.softuni.cardealer.entities.cars.Car;
import bg.softuni.cardealer.entities.customers.Customer;

import java.math.BigDecimal;

public class SaleImportDto {
    private Car car;

    private Customer customer;

    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal discountAmount;
    private BigDecimal priceWithDiscount;

    public SaleImportDto(Car car, Customer customer,
                         BigDecimal price, BigDecimal discount,
                         BigDecimal discountAmount, BigDecimal priceWithDiscount) {
        this.car = car;
        this.customer = customer;
        this.price = price;
        this.discount = discount;
        this.discountAmount = discountAmount;
        this.priceWithDiscount = priceWithDiscount;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
