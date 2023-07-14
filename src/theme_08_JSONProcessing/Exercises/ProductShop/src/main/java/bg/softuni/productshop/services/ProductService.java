package bg.softuni.productshop.services;

import bg.softuni.productshop.entities.categories.CategoryStatsDTO;
import bg.softuni.productshop.entities.products.ProductWithoutBuyerDTO;

import java.util.List;

public interface ProductService {
    List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(float from, float to);

    List<CategoryStatsDTO> getCategoryStatistics();
}
