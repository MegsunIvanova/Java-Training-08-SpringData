package bg.softuni.cardealer.entities.Sales;

import java.math.BigDecimal;

public class SaleByCustomersDto {

    private String fullName;

    private Long boughtCars;

    private BigDecimal spendMoney;

    public SaleByCustomersDto(String name, Long boughtCars, BigDecimal spendMoney) {
        this.fullName = name;
        this.boughtCars = boughtCars;
        this.spendMoney = spendMoney;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Long boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpendMoney() {
        return spendMoney;
    }

    public void setSpendMoney(BigDecimal spendMoney) {
        this.spendMoney = spendMoney;
    }
}
