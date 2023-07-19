package bg.softuni.productshop_xml.repositories;

import bg.softuni.productshop_xml.entities.users.User;
import bg.softuni.productshop_xml.entities.users.UserWithSoldProductsQ4Dto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u " +
            "JOIN u.sellingItems p " +
            "WHERE " +
            "   (SELECT COUNT(p) " +
            "   FROM Product p " +
            "   WHERE p.seller = u AND p.buyer IS NOT NULL) > 0 " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllWithSoldProductsAndBuyers();


    @Query("SELECT u FROM User u " +
            "WHERE (SELECT COUNT (p) FROM Product p " +
            "WHERE p.seller = u AND p.buyer IS NOT  NULL )  > 0 " +
            "ORDER BY (SELECT COUNT (p) FROM Product p " +
            "           WHERE p.seller = u AND p.buyer IS NOT  NULL ) Desc, " +
            "u.lastName ")
    List<User> findAllWithSoldProducts();

//    @Query("SELECT u, " +
//            "(SELECT COUNT(p) " +
//            "FROM Product p " +
//            "WHERE p.seller = u AND p.buyer IS NOT NULL) AS soldCount" +
//            "FROM User u " +
//            "WHERE soldCount > 0 " +
//            "ORDER BY soldCount DESC, u.lastName")
//    List<User> findAllWithSoldProductsOrderByCount();
}
