package bg.softuni.productshop.services;

import bg.softuni.productshop.entities.categories.CategoryStatsDTO;
import bg.softuni.productshop.entities.products.ProductWithoutBuyerDTO;
import bg.softuni.productshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;



    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<ProductWithoutBuyerDTO> getProductsInPriceRangeForSell(
            float from, float to) {
        BigDecimal rangeStart = BigDecimal.valueOf(from);
        BigDecimal rangeEnd = BigDecimal.valueOf(to);

        return this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc
                (rangeStart, rangeEnd);

    }

    @Override
    public List<CategoryStatsDTO> getCategoryStatistics() {
        return this.productRepository.getCategoryStats();
    }
}
