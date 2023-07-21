package bg.softuni.cardealer.repositories;

import bg.softuni.cardealer.entities.cars.Car;
import bg.softuni.cardealer.entities.cars.CarsToyotaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT new bg.softuni.cardealer.entities.cars.CarsToyotaDto " +
            "(c.id, c.make, c.model, c.travelledDistance) " +
            "FROM Car c WHERE c.make = :make ORDER BY c.model, c.travelledDistance desc ")
    List<CarsToyotaDto> findAllByMakeOrderByModelMakeAscTravelledDistanceDesc(String make);


}
