package bg.softuni.bookshop.domain.repositories;

import bg.softuni.bookshop.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//creating the repository interface will create table in the database
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByReleaseDateAfter(LocalDate releaseDate);

    int countFindByReleaseDateAfter(LocalDate releaseDate);

    List<Book> findByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);

}
