package bg.softuni.bookshop.domain.services;

import bg.softuni.bookshop.domain.entities.Author;
import bg.softuni.bookshop.domain.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

//by presumption all entities have to have a service that will manipulate the info from database
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        final long count = this.authorRepository.count();

        if (count != 0) {
            long randomId = new Random().nextLong(1L, count) + 1L;

            return this.authorRepository.findById(randomId).orElseThrow(NoSuchElementException::new);
        }

        throw new RuntimeException();

    }

    @Override
    public List<Author> findAuthorsWithBooksBeforeDate(LocalDate year1990) {
        return this.authorRepository.findDistinctByBooksReleaseDateBefore(year1990);
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

}
