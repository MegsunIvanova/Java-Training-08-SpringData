package bg.softuni.mvcdemo.repositories;

import bg.softuni.mvcdemo.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findFirstByName(String name);

    List<Project> findAllByIsFinished(boolean condition);
}
