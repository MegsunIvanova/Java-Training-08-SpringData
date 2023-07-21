package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarImportDTO;
import softuni.exam.models.dto.CarsWrapperDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.models.Constants.*;

@Service
public class CarsServiceImpl implements CarsService {
    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";

    private final CarsRepository carsRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public CarsServiceImpl(CarsRepository carsRepository, ValidationUtils validationUtils, ModelMapper modelMapper, XmlParser xmlParser) {
        this.carsRepository = carsRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Path.of(CARS_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        final StringBuilder stringBuilder = new StringBuilder();

        List<CarImportDTO> dTOs =
                this.xmlParser.fromFile(Path.of(CARS_FILE_PATH).toFile(), CarsWrapperDTO.class)
                        .getCars();

        for (CarImportDTO dto : dTOs) {
            stringBuilder.append(System.lineSeparator());

            if (this.carsRepository.findFirstByPlateNumber(dto.getPlateNumber()).isPresent() ||
                    !this.validationUtils.isValid(dto)) {
                stringBuilder.append(String.format(INVALID_FORMAT, CAR));

            } else {
                Car entity = this.modelMapper.map(dto, Car.class);
                this.carsRepository.save(entity);

                stringBuilder.append(String.format(SUCCESSFUL_FORMAT,
                        CAR,
                        dto.getCarMake() + " -",
                        dto.getCarModel()));
            }
        }

        return stringBuilder.toString().trim();
    }
}
