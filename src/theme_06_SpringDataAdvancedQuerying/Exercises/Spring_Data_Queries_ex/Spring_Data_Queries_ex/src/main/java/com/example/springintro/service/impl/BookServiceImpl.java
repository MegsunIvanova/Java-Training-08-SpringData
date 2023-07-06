package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService,
                           CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public List<String> findAllTitlesByAgeRestriction(String ageRestriction) {
        AgeRestriction restriction = AgeRestriction.valueOf(ageRestriction.toUpperCase());

        return this.bookRepository.findByAgeRestriction(restriction);
    }

    @Override
    public List<String> findAllTitlesByEditionTypeAndCopies(EditionType type, int copies) {

        return this.bookRepository.findByEditionTypeAndCopiesLessThan(type, copies)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllWithPriceNotBetween(double lowerBound, double upperBound) {
        BigDecimal lower = BigDecimal.valueOf(lowerBound);
        BigDecimal upper = BigDecimal.valueOf(upperBound);

        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(lower, upper)
                .stream()
                .map(b -> String.format("%s - $%.2f", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findNotReleasedIn(int releaseYear) {
        return this.bookRepository.findByReleaseDateYearNot(releaseYear);
    }

    @Override
    public List<String> findBooksReleasedBefore(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate before = LocalDate.parse(date, formatter);

        return this.bookRepository.findAllByReleaseDateBefore(before)
                .stream()
                .map(b -> String.format("%s %s %.2f", b.getTitle(), b.getEditionType(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksWithTitleContainingWith(String text) {

        return bookRepository.findAllByTitleContaining(text)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<String> findByAuthorLastNameStartsWith(String startsWith) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(startsWith)
                .stream()
                .map(b -> String.format("%s (%s %s)",
                        b.getTitle(),
                        b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public int countBooksWithTitleLongerThan(int length) {
        return this.bookRepository.countBooksWithTitleLongerThan(length);
    }

    @Override
    public List<String> getInformationByTitle(String title) {
        return this.bookRepository.findSummaryForTitle(title)
                .stream()
                .map(e -> String.format("%s %s %s %.2f",
                        e.getTitle(),
                        e.getEditionType().name(),
                        e.getAgeRestriction().name(),
                        e.getPrice()))
                .collect(Collectors.toList());

    }

    @Override
    public int addCopiesToBooksAfter(String date, int amount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate after = LocalDate.parse(date, formatter);

        return this.bookRepository.addCopiesTooBooksAfter(after, amount) * amount;
    }

    @Override
    public int deleteWithCopiesLessThan(int amount) {
        return this.bookRepository.deleteBookByCopiesLessThan(amount);
    }

    @Override
    public int getTotalBooksByAuthorWithUSPCall(String authorNames) {
        String[] names = authorNames.split("\\s+");

        return this.bookRepository.usp_get_total_Books_By_Author(names[0], names[1]);
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }

}
