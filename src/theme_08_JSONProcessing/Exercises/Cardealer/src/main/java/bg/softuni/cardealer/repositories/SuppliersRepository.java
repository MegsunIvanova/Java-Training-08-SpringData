package bg.softuni.cardealer.repositories;

import bg.softuni.cardealer.entities.suppliers.LocalSupplierDto;
import bg.softuni.cardealer.entities.suppliers.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuppliersRepository extends JpaRepository<Supplier, Integer> {

//    @Query("SELECT s FROM Supplier s WHERE NOT s.isImporter")
    @Query("SELECT new bg.softuni.cardealer.entities.suppliers.LocalSupplierDto" +
            " (s.id, s.name, count (p) ) " +
            "FROM Supplier s " +
            "JOIN s.parts p " +
            "WHERE NOT s.isImporter " +
            "GROUP BY s ")
    List<LocalSupplierDto> findAllByNotIsImporter();
}
