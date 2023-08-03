package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportConstellationDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.util.Constants.*;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private final String FILE_JSON_CONSTELLATIONS = "src/main/resources/files/json/constellations.json";
    private final ConstellationRepository constellationRepository;

    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_JSON_CONSTELLATIONS));
    }

    @Override
    public String importConstellations() throws IOException {
        ImportConstellationDTO[] constellationDTOs =
                gson.fromJson(this.readConstellationsFromFile(), ImportConstellationDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        for (ImportConstellationDTO dto : constellationDTOs) {

            Optional<Constellation> constellationByName = this.constellationRepository.findByName(dto.getName());

            if (constellationByName.isPresent() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, CONSTELLATION));
                continue;
            }

            Constellation entity = modelMapper.map(dto, Constellation.class);

            this.constellationRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    CONSTELLATION,
                    entity.getName() + " - " + entity.getDescription()));
        }

        return String.join(System.lineSeparator(), resultMessages);

    }
}
