package bg.softuni.springrepositories.services;

import bg.softuni.springrepositories.entities.Ingredient;
import bg.softuni.springrepositories.repositories.IngredientRepository;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

//@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;


    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    public List<Ingredient> findByName(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByNameStartingWith(name);

        for (Ingredient ingredient : ingredients) {
            System.out.printf("%s -> %d%n", ingredient.getName(), ingredient.getShampoos().size());
        }

        return ingredients;
    }

    @Override
    public List<Ingredient> findByNameWithin(List<String> names) {
        return ingredientRepository.findByNameInOrderByPriceAsc(names);
    }

    @Override
    public int increasePriceByPercentage(BigDecimal percent) {
        return ingredientRepository.increasePriceByPercentage(percent);
    }

    @Override
    public int deleteByName(String name) {
        return ingredientRepository.deleteByName(name);
    }

    @Override
    public int updatePriceByName(List<String> ingredientsNames, BigDecimal newPrice) {
        return ingredientRepository.updatePriceByName(ingredientsNames, newPrice);
    }
}
