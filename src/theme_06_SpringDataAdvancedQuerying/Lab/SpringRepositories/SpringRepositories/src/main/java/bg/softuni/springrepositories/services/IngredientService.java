package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {

    List<Ingredient> findByName(String name);

    List<Ingredient> findByNameWithin(List<String> names);

    int increasePriceByPercentage(BigDecimal percent);

    int deleteByName(String name);

    int updatePriceByName(List<String> ingredientsNames, BigDecimal newPrice);
}
