package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    @Query("SELECT b.title FROM Book b WHERE b.ageRestriction = :restriction")
    List<String> findByAgeRestriction(AgeRestriction restriction);

    List<Book> findByEditionTypeAndCopiesLessThan(EditionType type, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lessThan, BigDecimal higherThan);

    @Query("SELECT b.title FROM Book b WHERE year(b.releaseDate) != :releaseYear")
    List<String> findByReleaseDateYearNot (int releaseYear);


    List<Book> findAllByTitleContaining(String text);

    List<Book> findAllByAuthorLastNameStartingWith(String startsWith);

    @Query("SELECT COUNT (b) FROM  Book b WHERE length(b.title) > :length")
    int countBooksWithTitleLongerThan (int length);

    @Query("SELECT b.title AS title, " +
            "b.editionType AS editionType, " +
            "b.ageRestriction AS ageRestriction, " +
            "b.price AS price  " +
            "FROM Book b " +
            "WHERE b.title = :title")
    List<BookSummary> findSummaryForTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copies = b.copies + :amount " +
            "WHERE b.releaseDate > :after")
    int addCopiesTooBooksAfter(LocalDate after, long amount);

    @Transactional
    int deleteBookByCopiesLessThan(int amount);

    @Procedure
    int usp_get_total_Books_By_Author (String firstName, String lastName);

}
