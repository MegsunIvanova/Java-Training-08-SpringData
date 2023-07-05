package bg.softuni.springrepositories.repositories;

import bg.softuni.springrepositories.entities.Ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByNameStartingWith(String name);

    List<Ingredient> findByNameInOrderByPriceAsc(List<String> names);

    @Query("UPDATE Ingredient AS i SET i.price =  i.price + i.price * :multiplier")
    @Modifying
    @Transactional
    int increasePriceByPercentage(@Param("multiplier") BigDecimal percent);

    @Transactional
    int deleteByName(String name);

    @Query("UPDATE Ingredient AS i SET i.price = :newPrice WHERE i.name IN :ingredientsNames")
    @Modifying
    @Transactional
    int updatePriceByName(List<String> ingredientsNames, BigDecimal newPrice);
}
