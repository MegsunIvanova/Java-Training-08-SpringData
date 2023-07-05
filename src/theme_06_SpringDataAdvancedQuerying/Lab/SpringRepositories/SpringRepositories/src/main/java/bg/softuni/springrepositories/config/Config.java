package bg.softuni.springrepositories.config;

import bg.softuni.springrepositories.repositories.IngredientRepository;
import bg.softuni.springrepositories.services.IngredientService;
import bg.softuni.springrepositories.services.IngredientServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Config {

    private final Environment environment;

    public Config(Environment environment) {
        this.environment = environment;

        int property = this.environment.getProperty("bg.softuni.max-shampoos", int.class);

        System.out.println(property);

    }

    @Bean
    public IngredientService createIngredientService(IngredientRepository ingredientRepo) {
        return new IngredientServiceImpl(ingredientRepo);
    }


}
