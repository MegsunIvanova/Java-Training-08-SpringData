package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportTownDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;

@Service
public class TownServiceImpl implements TownService {

    private final String TOWNS_JSON_FILE = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_JSON_FILE));
    }

    @Override
    public String importTowns() throws IOException {
        ImportTownDTO[] dTOsFromJson = gson.fromJson(this.readTownsFileContent(), ImportTownDTO[].class);

        List<String> result = new ArrayList<>();

        for (ImportTownDTO dto : dTOsFromJson) {
            Optional<Town> townByName = this.townRepository.findFirstByTownName(dto.getTownName());

            if (townByName.isPresent() || validationUtils.isInvalid(dto)) {
                result.add(String.format(INVALID_DATA_FORMAT, TOWN));
                continue;
            }

            Town entity = modelMapper.map(dto, Town.class);

            this.townRepository.save(entity);

            result.add(String.format(SUCCESSFUL_DATA_FORMAT,
                    TOWN,
                    entity.getTownName() + " -",
                    entity.getPopulation()));

        }

        return String.join(System.lineSeparator(), result);
    }
}
