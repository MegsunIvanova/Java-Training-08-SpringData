package bg.softuni.mvcdemo.repositories;

import bg.softuni.mvcdemo.entities.Employee;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Registered
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByAgeGreaterThan(Integer age);
}
