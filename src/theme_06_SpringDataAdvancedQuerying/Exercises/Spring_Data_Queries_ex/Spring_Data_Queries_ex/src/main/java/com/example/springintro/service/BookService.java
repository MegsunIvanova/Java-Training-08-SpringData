package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllTitlesByEditionTypeAndCopies(EditionType type, int copies);

    List<String> findAllWithPriceNotBetween(double lowerBound, double upperBound);

    List<String> findNotReleasedIn(int releaseYear);

    List<String> findBooksReleasedBefore(String date);

    List<String> findAllBooksWithTitleContainingWith(String text);

    List<String> findByAuthorLastNameStartsWith(String startsWith);

    int countBooksWithTitleLongerThan(int length);

    List<String> getInformationByTitle(String title);

    int addCopiesToBooksAfter(String date, int amount);

    int deleteWithCopiesLessThan(int amount);

    int getTotalBooksByAuthorWithUSPCall(String authorNames);
}
