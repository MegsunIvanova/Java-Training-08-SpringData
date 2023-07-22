package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;

@Service
public class CountryServiceImpl implements CountryService {

    private final String IMPORT_COUNTRIES_FILE = "src/main/resources/files/json/countries.json";
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(IMPORT_COUNTRIES_FILE));
    }

    @Override
    public String importCountries() throws IOException {
        ImportCountryDTO[] dTOs = gson.fromJson(readCountriesFromFile(), ImportCountryDTO[].class);

        List<String> result = new ArrayList<>();

        for (ImportCountryDTO dTO : dTOs) {
            Optional<Country> optionalCountry =
                    this.countryRepository.findFirstByCountryName(dTO.getCountryName());

            if (optionalCountry.isPresent() || validationUtils.isInvalid(dTO)) {
                result.add(String.format(INVALID_MESSAGE_FORMAT, COUNTRY));
                continue;
            }

            Country entity = modelMapper.map(dTO, Country.class);

            this.countryRepository.save(entity);

            result.add(String.format(SUCCESSFUL_MESSAGE_FORMAT,
                    COUNTRY,
                    entity.getCountryName(),
                    entity.getCurrency()));

        }

        return String.join(System.lineSeparator(), result);
    }
}
