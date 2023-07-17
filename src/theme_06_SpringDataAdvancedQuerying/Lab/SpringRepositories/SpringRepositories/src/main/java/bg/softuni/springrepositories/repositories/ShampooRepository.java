package bg.softuni.springrepositories.repositories;

import bg.softuni.springrepositories.entities.Shampoo;
import bg.softuni.springrepositories.entities.Size;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findBySizeOrderById(Size size);

    List<Shampoo> findBySizeOrLabelIdOrderByPrice(Size size, long labelId);

    List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countFindByPriceLessThan(BigDecimal price);

    @Query("SELECT s " +
            "FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i.name IN :ingredientsNames")
    List<Shampoo> findByIngredientsNameIn(List<String> ingredientsNames);

    //    @Query("SELECT s, COUNT(i) FROM Shampoo AS s JOIN s.ingredients AS i " +
//            "GROUP BY s HAVING COUNT(i) < :ingredientsNumber")
    @Query("SELECT s FROM Shampoo AS s WHERE size( s.ingredients) < :ingredientsCount")
    List<Shampoo> findByIngredientCountLessThan(int ingredientsCount);

    @Query("UPDATE Shampoo  AS s SET s.price = :newPrice WHERE s.id = :id")
    @Modifying
    @Transactional
    void updatePriceById(BigDecimal newPrice, Long id);
}
