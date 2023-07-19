package bg.softuni.productshop_xml.services;

import bg.softuni.productshop_xml.entities.categories.Category;
import bg.softuni.productshop_xml.entities.categories.ExportCategoriesWithProductsDTO;
import bg.softuni.productshop_xml.entities.categories.CategoryWithProductsDTO;
import bg.softuni.productshop_xml.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ExportCategoriesWithProductsDTO findCategoriesWithNumberOfProducts() {

        List<CategoryWithProductsDTO> categoriesDTOs =
                this.categoryRepository.findAllByOrderByProductsSizeDesc();

        return new ExportCategoriesWithProductsDTO(categoriesDTOs);
    }
}
