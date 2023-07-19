package bg.softuni.productshop_xml.repositories;

import bg.softuni.productshop_xml.entities.categories.Category;
import bg.softuni.productshop_xml.entities.categories.CategoryWithProductsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query ("SELECT new bg.softuni.productshop_xml.entities.categories.CategoryWithProductsDTO (" +
            "c.name, size(c.products), AVG(p.price), SUM(p.price)) " +
            "FROM Category c JOIN c.products p " +
            "GROUP BY c " +
            "ORDER BY size(c.products) DESC ")
    List<CategoryWithProductsDTO> findAllByOrderByProductsSizeDesc();
}
