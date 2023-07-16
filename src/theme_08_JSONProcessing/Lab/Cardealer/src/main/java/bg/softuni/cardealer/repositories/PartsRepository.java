package bg.softuni.cardealer.repositories;

import bg.softuni.cardealer.entities.parts.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartsRepository extends JpaRepository<Part, Integer> {
}
