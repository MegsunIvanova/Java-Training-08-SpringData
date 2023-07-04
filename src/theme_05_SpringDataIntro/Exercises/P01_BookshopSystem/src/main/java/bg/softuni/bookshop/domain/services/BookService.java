package bg.softuni.bookshop.domain.services;

import bg.softuni.bookshop.domain.entities.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    List<Book> findBooksReleaseDateAfter(LocalDate date);

    int countFindBooksReleaseDateAfter(LocalDate date);

    List<Book> findByAuthorNamesOrderedByDateDescAndTitleAsc(String firstName, String lastName);
}

