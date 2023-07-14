package bg.softuni.productshop.entities.products;

import bg.softuni.productshop.entities.users.User;

import java.math.BigDecimal;

public class ProductImportDTO {

    private String name;

    private BigDecimal price;

    private User seller;

    private User buyer;

    public ProductImportDTO(String name, BigDecimal price, User seller, User buyer) {
        this.name = name;
        this.price = price;
        this.seller = seller;
        this.buyer = buyer;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public User getSeller() {
        return seller;
    }

    public User getBuyer() {
        return buyer;
    }
}
