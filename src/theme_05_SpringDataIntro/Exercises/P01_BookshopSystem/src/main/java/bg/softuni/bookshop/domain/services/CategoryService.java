package bg.softuni.bookshop.domain.services;

import bg.softuni.bookshop.domain.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}

