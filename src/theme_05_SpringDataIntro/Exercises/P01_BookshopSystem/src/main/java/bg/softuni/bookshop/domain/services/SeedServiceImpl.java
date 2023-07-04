package bg.softuni.bookshop.domain.services;

import bg.softuni.bookshop.domain.entities.Author;
import bg.softuni.bookshop.domain.entities.Book;
import bg.softuni.bookshop.domain.entities.Category;
import bg.softuni.bookshop.domain.enums.AgeRestriction;
import bg.softuni.bookshop.domain.enums.EditionType;
import bg.softuni.bookshop.domain.repositories.AuthorRepository;
import bg.softuni.bookshop.domain.repositories.BookRepository;
import bg.softuni.bookshop.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.bookshop.constants.FilePath.*;

// only purpose of this service is to populate the database
@Service
public class SeedServiceImpl implements SeedService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of(RESOURCE_PATH + AUTHORS_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .map(s -> s.split("\\s+"))
                .map(names -> new Author(names[0], names[1]))
                .forEach(authorRepository::save);
    }

    @Override
    public void seedCategories() throws IOException {
        Files.readAllLines(Path.of(RESOURCE_PATH + CATEGORY_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .map(Category::new)
                .forEach(categoryRepository::save);
    }

    @Override
    public void seedBooks() throws IOException {
        Files.readAllLines(Path.of(RESOURCE_PATH + BOOKS_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .map(this::getBookObject)
                .forEach(bookRepository::save);
    }

    private Book getBookObject(String line) {

        String[] bookParts = line.split("\\s+");

        EditionType editionType = EditionType.values()[Integer.parseInt(bookParts[0])];

        LocalDate releaseDate =
                LocalDate.parse(bookParts[1], DateTimeFormatter.ofPattern("d/M/yyyy"));

        int copies = Integer.parseInt(bookParts[2]);

        BigDecimal price = new BigDecimal(bookParts[3]);

        AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(bookParts[4])];

        String title = Arrays.stream(bookParts)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();

        Set<Category> categories = categoryService.getRandomCategories();

        return new Book(title, editionType, price, copies, releaseDate,
                ageRestriction, author, categories);
    }

}