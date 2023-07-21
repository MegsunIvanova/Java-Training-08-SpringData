package bg.softuni.cardealer.entities.Sales;

import bg.softuni.cardealer.entities.cars.Car;
import bg.softuni.cardealer.entities.cars.CarDto;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class SaleWithDiscountDto {
        private CarDto car;

        private String customerName;

        private BigDecimal discount;

        private BigDecimal price;

        private BigDecimal priceWithDiscount;

        public SaleWithDiscountDto(Car car, String customerName, BigDecimal discount, BigDecimal price, BigDecimal priceWithDiscount) {
                this.car = new ModelMapper().map(car, CarDto.class);
                this.customerName = customerName;
                this.discount = discount;
                this.price = price;
                this.priceWithDiscount = priceWithDiscount;
        }
}
