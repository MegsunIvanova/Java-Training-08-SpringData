package bg.softuni.bookshop.domain.services;

import bg.softuni.bookshop.domain.entities.Category;
import bg.softuni.bookshop.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getRandomCategories() {
        final long count = this.categoryRepository.count();

        if (count == 0) {
            throw new RuntimeException();
        }

        Random random = new Random();

        Set<Long> categoriesIds = new HashSet<>();

        for (int i = 0; i < count; i++) {
            long randomId = new Random().nextLong(1L, count) + 1L;

            categoriesIds.add(randomId);

        }

        List<Category> allById = this.categoryRepository.findAllById(categoriesIds);


        return new HashSet<>(allById);

    }


}
