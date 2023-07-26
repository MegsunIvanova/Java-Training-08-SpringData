package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportPassengerView;
import softuni.exam.models.entity.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findFirstByEmail(String email);

//    @Query("SELECT new softuni.exam.models.dto.ExportPassengerView( " +
//            "p.firstName, p.lastName, p.email, p.phoneNumber, count(t) ) " +
//            "FROM Passenger p JOIN p.tickets t " +
//            "GROUP BY p " +
//            "ORDER BY count(t) DESC , p.email ASC ")
    @Query("SELECT new softuni.exam.models.dto.ExportPassengerView( " +
            "p.firstName, p.lastName, p.email, p.phoneNumber, size(p.tickets) ) " +
            "FROM Passenger p " +
            "GROUP BY p " +
            "ORDER BY size(p.tickets) DESC , p.email ASC ")
    List<ExportPassengerView> findAllByOrderByTicketsCountDescEmail();

}
