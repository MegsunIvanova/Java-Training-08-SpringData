package bg.softuni.cardealer.services;

import bg.softuni.cardealer.entities.cars.CarAndPartsListDto;
import bg.softuni.cardealer.entities.cars.CarsToyotaDto;

import java.util.List;

public interface CarsService {
    List<CarsToyotaDto> getAllToyotaCarsOrderedByModelAndDistance();

    List<CarAndPartsListDto> getAllCarsWithPartsList();
}
