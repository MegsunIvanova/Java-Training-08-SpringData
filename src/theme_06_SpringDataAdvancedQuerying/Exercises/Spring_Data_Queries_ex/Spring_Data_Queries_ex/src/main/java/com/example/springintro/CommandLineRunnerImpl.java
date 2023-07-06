package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedData();

//        p01_printBookTitlesByAgeRestriction();
//        p02_printGoldenBooksTitlesWithLessThan5000Copies();
//        p03_printBooksTitleAndPriceWithPriceLowerThan5AndHigherThan40();
//        p04_printBooksTitlesNotReleasedInYear();
//        p05_printBooksTitleEditionAndPriceReleasedBeforeDate();
//        p06_printAuthorsNamesWithFirstNameEndsWith();
//        p07_printBookTitlesContainingWith();
//        p08_printBooksAndAuthorsWithAuthorLastNameStartsWith();
//        p09_printCountOfBooksWithTitleLengthLongerThan();
//        p10_printTotalBooksCopiesByAuthorOrderByCopiesDesc();
//        p11_printBookInformationByTitle();
//        p12_increaseCopiesForBooksReleasedAfter();
//        p13_removeBooksWithCopiesLessThan();
        p14_getTotalBooksByAuthorWithUSPCall();
    }

    private void p01_printBookTitlesByAgeRestriction() {
        Scanner scanner = new Scanner(System.in);
        String restriction = scanner.nextLine();

        List<String> result = this.bookService.findAllTitlesByAgeRestriction(restriction);

        result.forEach(System.out::println);
    }

    private void p02_printGoldenBooksTitlesWithLessThan5000Copies() {
        List<String> result =
                this.bookService.findAllTitlesByEditionTypeAndCopies(EditionType.GOLD, 5000);

        result.forEach(System.out::println);
    }

    private void p03_printBooksTitleAndPriceWithPriceLowerThan5AndHigherThan40() {
        List<String> result =
                this.bookService.findAllWithPriceNotBetween(5, 40);

        result.forEach(System.out::println);
    }

    private void p04_printBooksTitlesNotReleasedInYear() {
        Scanner scanner = new Scanner(System.in);

        int year = Integer.parseInt(scanner.nextLine());

        List<String> result =
                this.bookService.findNotReleasedIn(year);

        result.forEach(System.out::println);
    }

    private void p05_printBooksTitleEditionAndPriceReleasedBeforeDate() {
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();

        List<String> result = this.bookService.findBooksReleasedBefore(date);

        result.forEach(System.out::println);
    }

    private void p06_printAuthorsNamesWithFirstNameEndsWith() {
        Scanner scanner = new Scanner(System.in);
        String endsWith = scanner.nextLine();

        List<String> result = this.authorService.findByFirstNameEndsWith(endsWith);

        result.forEach(System.out::println);
    }

    private void p07_printBookTitlesContainingWith() {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        List<String> result = this.bookService.findAllBooksWithTitleContainingWith(text);

        result.forEach(System.out::println);

    }

    private void p08_printBooksAndAuthorsWithAuthorLastNameStartsWith() {
        Scanner scanner = new Scanner(System.in);
        String startsWith = scanner.nextLine();

        List<String> result = this.bookService.findByAuthorLastNameStartsWith(startsWith);

        result.forEach(System.out::println);
    }

    private void p09_printCountOfBooksWithTitleLengthLongerThan() {
        Scanner scanner = new Scanner(System.in);
        int length = Integer.parseInt(scanner.nextLine());

        int result = this.bookService.countBooksWithTitleLongerThan(length);

        System.out.println(result);
    }

    private void p10_printTotalBooksCopiesByAuthorOrderByCopiesDesc() {
        List<String> result = this.authorService.getWithTotalCopiesOrderByCopiesDesc();

        result.forEach(System.out::println);
    }

    private void p11_printBookInformationByTitle() {
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();

        List<String> result = this.bookService.getInformationByTitle(title);

        result.forEach(System.out::println);

    }

    private void p12_increaseCopiesForBooksReleasedAfter() {
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        int amount = Integer.parseInt(scanner.nextLine());

        int result = this.bookService.addCopiesToBooksAfter(date, amount);

        System.out.println(result);
    }

    private void p13_removeBooksWithCopiesLessThan() {
        Scanner scanner = new Scanner(System.in);
        int amount = Integer.parseInt(scanner.nextLine());

        int result = this.bookService.deleteWithCopiesLessThan(amount);

        System.out.println(result);
    }

    private void p14_getTotalBooksByAuthorWithUSPCall() {
        Scanner scanner = new Scanner(System.in);
        String authorNames = scanner.nextLine();

        int result = this.bookService.getTotalBooksByAuthorWithUSPCall(authorNames);

        System.out.println(result);

    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
