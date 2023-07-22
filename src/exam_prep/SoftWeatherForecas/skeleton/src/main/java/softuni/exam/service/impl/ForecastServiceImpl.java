package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportForecastDTO;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.dto.ImportForecastDTO;
import softuni.exam.models.dto.ImportForecastWrapperDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;
import static softuni.exam.models.Constants.CITY;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final String IMPORT_FORECASTS_FILE = "src/main/resources/files/xml/forecasts.xml";

    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;

    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository,
                               CityRepository cityRepository,
                               XmlParser xmlParser, ModelMapper modelMapper,
                               ValidationUtils validationUtils) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(IMPORT_FORECASTS_FILE));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        List<ImportForecastDTO> dTOs
                = xmlParser.fromFile(IMPORT_FORECASTS_FILE, ImportForecastWrapperDTO.class).getForecasts();

        List<String> result = new ArrayList<>();

        for (ImportForecastDTO dTO : dTOs) {
            Optional<City> city = this.cityRepository.findById(dTO.getCityId());

            DayOfWeek dayOfWeek;

            try {
                dayOfWeek = DayOfWeek.valueOf(dTO.getDayOfWeek());
            } catch (IllegalArgumentException e) {
                result.add(String.format(INVALID_MESSAGE_FORMAT, FORECAST));
                continue;
            }

            if (city.isEmpty() || validationUtils.isInvalid(dTO)) {
                result.add(String.format(INVALID_MESSAGE_FORMAT, FORECAST));
                continue;
            }

            Optional<Forecast> optionalForecast = this.forecastRepository
                    .findByDayOfWeekAndCityCityName(dayOfWeek, city.get().getCityName());

            if (optionalForecast.isPresent()) {
                result.add(String.format(INVALID_MESSAGE_FORMAT, FORECAST));
                continue;
            }

            Forecast entity = modelMapper.map(dTO, Forecast.class);

            entity.setCity(city.get());
            entity.setSunrise(LocalTime.parse(dTO.getSunrise()));
            entity.setSunset(LocalTime.parse(dTO.getSunset()));

            this.forecastRepository.save(entity);

            result.add(String.format(SUCCESSFUL_MESSAGE_FORMAT,
                    FORECAST,
                    entity.getDayOfWeek().name(),
                    entity.getMaxTemperature()));
        }

        return String.join(System.lineSeparator(), result);
    }

    @Override
    public String exportForecasts() {

        List<Forecast> forecasts =
                this.forecastRepository
                        .findAllByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescIdAsc
                                (DayOfWeek.SUNDAY, 150000);

        return forecasts.stream()
                .map(entity -> modelMapper.map(entity, ExportForecastDTO.class))
                .map(ExportForecastDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
//        return null;
    }
}
