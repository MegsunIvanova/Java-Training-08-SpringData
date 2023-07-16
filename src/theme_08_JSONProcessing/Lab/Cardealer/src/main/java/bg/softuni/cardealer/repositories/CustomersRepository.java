package bg.softuni.cardealer.repositories;

import bg.softuni.cardealer.entities.customers.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c ORDER BY c.birthDate ASC, c.isYoungDriver ASC ")
    List<Customer> findAllByOrderByBirthDateAscYoungDriverAsc ();
}
