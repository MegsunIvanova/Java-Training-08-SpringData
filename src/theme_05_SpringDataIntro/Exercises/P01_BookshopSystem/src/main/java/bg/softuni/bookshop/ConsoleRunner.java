package bg.softuni.bookshop;

import bg.softuni.bookshop.domain.entities.Author;
import bg.softuni.bookshop.domain.entities.Book;
import bg.softuni.bookshop.domain.repositories.AuthorRepository;
import bg.softuni.bookshop.domain.repositories.BookRepository;
import bg.softuni.bookshop.domain.services.AuthorService;
import bg.softuni.bookshop.domain.services.BookService;
import bg.softuni.bookshop.domain.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

//after suc compilation of our program run method will be executed
@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;

    private final BookService bookService;

    private final AuthorService authorService;


    @Autowired
    public ConsoleRunner(SeedService seedService, BookService bookService, BookRepository bookRepository,
                         AuthorService authorService, AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    private void _01_bookAfter2000() {
        LocalDate year2000 = LocalDate.of(2000, 12, 31);

        List<Book> books = this.bookService.findBooksReleaseDateAfter(year2000);
        books.forEach(b -> System.out.println(b.getTitle()));

        int count = this.bookService.countFindBooksReleaseDateAfter(year2000);
        System.out.println("Total count: " + count);
    }

    private void _02_allAuthorsWithBookBefore1990() {
        LocalDate year1990 = LocalDate.of(1990, 1, 1);

        List<Author> authors = this.authorService.findAuthorsWithBooksBeforeDate(year1990);

        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void _03_allAuthorsOrderByBookCount() {
        List<Author> authors = this.authorService.findAll();

        authors.stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(a -> {
                    System.out.printf("%s %s -> %d%n",
                            a.getFirstName(),
                            a.getLastName(),
                            a.getBooks().size());
                });
    }

    private void _04_allBooksByAuthorOrderByDateAndTitle() {
        String firstName = "George";
        String lastName = "Powell";
        List<Book> books = this.bookService
                .findByAuthorNamesOrderedByDateDescAndTitleAsc(firstName, lastName);

        books.forEach(b -> System.out.println(b.getTitle() + " " + b.getReleaseDate() + " " + b.getCopies()));
    }

    @Override
    public void run(String... args) throws Exception {

//        this.seedService.seedAllData();

//        this._01_bookAfter2000();

//        this._02_allAuthorsWithBookBefore1990();

//        this._03_allAuthorsOrderByBookCount();

        this._04_allBooksByAuthorOrderByDateAndTitle();
    }


}
