package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<Shampoo> findByBrand(String brand);
    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findBySizeOrderById(Size size);

    List<Shampoo> findBySizeOrLabelId(Size size, long labelId);

    List<Shampoo> findByPriceGreaterThan(BigDecimal bigDecimal);

    int findCheaperThanCount(BigDecimal bigDecimal);

    List<Shampoo> findAllWithIngredients(List<String> ingredientsNames);

    List<Shampoo> findByIngredientCountLessThan(int ingredientsNumber);
}
