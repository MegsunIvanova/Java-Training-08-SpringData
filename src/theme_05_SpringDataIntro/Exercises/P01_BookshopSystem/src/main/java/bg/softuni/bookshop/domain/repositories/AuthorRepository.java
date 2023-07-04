package bg.softuni.bookshop.domain.repositories;

import bg.softuni.bookshop.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//creating the repository interface will create table in the database
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findDistinctByBooksReleaseDateBefore(LocalDate year1990);

}

