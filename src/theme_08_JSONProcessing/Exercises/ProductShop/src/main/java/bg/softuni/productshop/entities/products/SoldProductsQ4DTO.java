package bg.softuni.productshop.entities.products;

import java.util.ArrayList;
import java.util.List;

public class SoldProductsQ4DTO {

    private int count;

    private List<ProductWithNameAndPriceDTO> soldProducts;

    public SoldProductsQ4DTO() {
        soldProducts = new ArrayList<>();
    }

    public SoldProductsQ4DTO(List<ProductWithNameAndPriceDTO> soldProducts) {
        this.count = soldProducts.size();
        this.soldProducts = soldProducts;
    }
}
