package bg.softuni.cardealer.entities.Sales;

import java.math.BigDecimal;

public class SaleOrderedDto {

    private Integer id;

    private BigDecimal discount;

    private Integer carId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
