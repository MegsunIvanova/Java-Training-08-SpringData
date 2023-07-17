package bg.softuni.springjson.dtos.json_nested;

import java.math.BigDecimal;

public class ProductDTO {
    private String name;
    private BigDecimal price;

    public ProductDTO() {
    }

    public ProductDTO(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
