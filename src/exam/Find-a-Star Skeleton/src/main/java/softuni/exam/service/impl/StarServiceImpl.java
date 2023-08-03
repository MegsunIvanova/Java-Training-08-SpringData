package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportStarsDTO;
import softuni.exam.models.dto.ImportConstellationDTO;
import softuni.exam.models.dto.ImportStarDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.util.Constants.*;
import static softuni.exam.util.Constants.CONSTELLATION;

@Service
public class StarServiceImpl implements StarService {

    private final String FILE_JSON_STARS = "src/main/resources/files/json/stars.json";

    private final StarRepository starRepository;
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public StarServiceImpl(StarRepository starRepository, ConstellationRepository constellationRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_JSON_STARS));
    }

    @Override
    public String importStars() throws IOException {
        ImportStarDTO[] starDTOs =
                gson.fromJson(this.readStarsFileContent(), ImportStarDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        for (ImportStarDTO dto : starDTOs) {

            Optional<Star> starByName = this.starRepository.findByName(dto.getName());
            Optional<Constellation> constellationById = this.constellationRepository.findById(dto.getConstellation());

            if (starByName.isPresent() || constellationById.isEmpty() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, STAR));
                continue;
            }

            Star entity = modelMapper.map(dto, Star.class);
            entity.setConstellation(constellationById.get());

            this.starRepository.save(entity);

            String msg = String.format("%s - %.2f light years", entity.getName(), entity.getLightYears());

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    STAR,
                    msg));
        }

        return String.join(System.lineSeparator(), resultMessages);

    }

    @Override
    public String exportStars() {

        List<Star> stars =
                this.starRepository
                        .findAllByAstronomersIsEmptyAndStarTypeOrderByLightYears(StarType.RED_GIANT);

        return stars
                .stream()
                .map(e -> modelMapper.map(e, ExportStarsDTO.class))
                .map(ExportStarsDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
