package bg.softuni.bookshop.domain.services;

import bg.softuni.bookshop.domain.entities.Book;
import bg.softuni.bookshop.domain.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findBooksReleaseDateAfter(LocalDate date) {
        return this.bookRepository.findByReleaseDateAfter(date);
    }

    @Override
    public int countFindBooksReleaseDateAfter(LocalDate date) {
        return this.bookRepository.countFindByReleaseDateAfter(date);
    }

    @Override
    public List<Book> findByAuthorNamesOrderedByDateDescAndTitleAsc(String firstName, String lastName) {
        return this.bookRepository.findByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(firstName, lastName);
    }
}
