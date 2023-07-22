package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;
import static softuni.exam.models.Constants.COUNTRY;

@Service
public class CityServiceImpl implements CityService {
    private final String IMPORT_CITIES_FILE = "src/main/resources/files/json/cities.json";

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(IMPORT_CITIES_FILE));
    }

    @Override
    public String importCities() throws IOException {
        ImportCityDTO[] dTOs = gson.fromJson(readCitiesFileContent(), ImportCityDTO[].class);

        List<String> result = new ArrayList<>();

        TypeMap<ImportCityDTO, City> typeMap =
                modelMapper.createTypeMap(ImportCityDTO.class, City.class);
        typeMap.addMappings(mapper -> mapper.skip(City::setId));
        typeMap.addMappings(mapper -> mapper.skip(City::setCountry));

        for (ImportCityDTO dTO : dTOs) {
            Optional<City> optionalCity =
                    this.cityRepository.findFirstByCityName(dTO.getCityName());

            Optional<Country> country = this.countryRepository.findById(dTO.getCountryId());

            if (optionalCity.isPresent() || country.isEmpty() || validationUtils.isInvalid(dTO)) {
                result.add(String.format(INVALID_MESSAGE_FORMAT, CITY));
                continue;
            }

            City entity = typeMap.map(dTO);

            entity.setCountry(country.get());

            this.cityRepository.save(entity);

            result.add(String.format(SUCCESSFUL_MESSAGE_FORMAT,
                    CITY,
                    entity.getCityName(),
                    entity.getPopulation()));
        }

        return String.join(System.lineSeparator(), result);
    }
}
