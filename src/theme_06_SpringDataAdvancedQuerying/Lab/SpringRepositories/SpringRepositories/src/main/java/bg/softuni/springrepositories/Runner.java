package bg.softuni.springrepositories;

import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.services.IngredientService;
import bg.softuni.springrepositories.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public Runner(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Shampoo> result = shampooService.findByBrand("Swiss Green Apple & Nettle");

//        List<Shampoo> result
//                = shampooService.findByBrandAndSize("Swiss Green Apple & Nettle", Size.SMALL);

        //1. Select Shampoos by Size
//        List<Shampoo> result = shampooService.findBySizeOrderById(Size.MEDIUM);

        //2. Select Shampoos by Size or Label
//        List<Shampoo> result = shampooService.findBySizeOrLabelId(Size.MEDIUM, 10L);

        //3. Select Shampoos by Price
//        List<Shampoo> result = shampooService.findByPriceGreaterThan(BigDecimal.valueOf(5));

        //4. Select Ingredients by Name
//        List<Ingredient> result = ingredientService.findByName("M");

        //5. Select Ingredients by Names
//        List<Ingredient> result = ingredientService.findByNameWithin(List.of("Lavender", "Herbs", "Apple"));

        //6. Count Shampoos by Price
//        int count = shampooService.findCheaperThanCount(BigDecimal.valueOf(8.50));
//        System.out.println(count);

        //7. Select Shampoos by Ingredients
//        List<Shampoo> result = shampooService.findAllWithIngredients(List.of("Berry", "Mineral-Collagen"));

        //8. Select Shampoos by Ingredients Count
//        List<Shampoo> result = shampooService.findByIngredientCountLessThan(2);

        //9. Delete Ingredients by Name
//        int deleted = ingredientService.deleteByName("Nettle");
//        System.out.println("Deleted: " + deleted);

        //10. Update Ingredients by Price
//        int updated = ingredientService.increasePriceByPercentage(BigDecimal.valueOf(0.1));
//        System.out.println("Updated: " + updated);

        //11. Update Ingredients by Names
//        int updated = ingredientService.updatePriceByName(List.of("Berry", "Mineral-Collagen"), BigDecimal.valueOf(10));
//        System.out.println("Updated: " + updated);

//        result.forEach(System.out::println);

        //Q/A Additional
//        Shampoo shampoo = shampooService.findByBrand("Swiss Green Apple & Nettle").get(0);
//        shampoo.setPrice(BigDecimal.TEN);
//        shampooService.save(shampoo);

        shampooService.updatePriceForId(BigDecimal.ONE, 1L);
    }
}
