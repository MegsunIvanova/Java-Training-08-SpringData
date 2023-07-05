package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.entities.Size;
import bg.softuni.springrepositories.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return shampooRepository.findByBrand(brand);
    }

    @Override
    public List<Shampoo> findByBrandAndSize(String brand, Size size) {
        return shampooRepository.findByBrandAndSize(brand, size);
    }

    @Override
    public List<Shampoo> findBySizeOrderById(Size size) {
        return shampooRepository.findBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> findBySizeOrLabelId(Size size, long labelId) {
        return shampooRepository.findBySizeOrLabelIdOrderByPrice(size, labelId);
    }

    @Override
    public List<Shampoo> findByPriceGreaterThan(BigDecimal price) {
        return shampooRepository.findByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int findCheaperThanCount(BigDecimal price) {
        return shampooRepository.countFindByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> findAllWithIngredients(List<String> ingredientsNames) {
        return shampooRepository.findByIngredientsNameIn(ingredientsNames);
    }

    @Override
    public List<Shampoo> findByIngredientCountLessThan(int ingredientsCount) {
        return shampooRepository.findByIngredientCountLessThan(ingredientsCount);
    }
}
