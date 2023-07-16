package bg.softuni.cardealer.services.impl;

import bg.softuni.cardealer.entities.cars.Car;
import bg.softuni.cardealer.entities.cars.CarAndPartsListDto;
import bg.softuni.cardealer.entities.cars.CarsToyotaDto;
import bg.softuni.cardealer.repositories.CarsRepository;
import bg.softuni.cardealer.services.CarsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarsServiceImpl implements CarsService {

    private final ModelMapper mapper;
    private final CarsRepository carsRepository;

    public CarsServiceImpl(ModelMapper mapper, CarsRepository carsRepository) {
        this.mapper = mapper;
        this.carsRepository = carsRepository;
    }

    @Override
    public List<CarsToyotaDto> getAllToyotaCarsOrderedByModelAndDistance() {
        return this.carsRepository.findAllByMakeOrderByModelMakeAscTravelledDistanceDesc("Toyota");

    }

    @Override
    @Transactional
    public List<CarAndPartsListDto> getAllCarsWithPartsList() {
        return this.carsRepository.findAll()
                .stream()
                .map(car -> mapper.map(car, CarAndPartsListDto.class))
                .toList();

    }
}
