package bg.softuni.bookshop.domain.services;

import bg.softuni.bookshop.domain.entities.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorService {

   Author getRandomAuthor();

    List<Author> findAuthorsWithBooksBeforeDate(LocalDate year1990);

    List<Author> findAll();
}

