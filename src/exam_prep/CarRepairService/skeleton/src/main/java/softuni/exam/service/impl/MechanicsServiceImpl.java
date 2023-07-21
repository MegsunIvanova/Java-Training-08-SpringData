package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;

@Service
public class MechanicsServiceImpl implements MechanicsService {

    private static final String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";

    private final MechanicsRepository mechanicsRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Override
    public String toString() {
        return super.toString();
    }

    @Autowired
    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.mechanicsRepository = mechanicsRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Path.of(MECHANICS_FILE_PATH));
    }

    @Override
    public String importMechanics() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        List<MechanicImportDTO> mechanicImportDTOS =
                Arrays.stream(this.gson.fromJson(readMechanicsFromFile(), MechanicImportDTO[].class))
                        .collect(Collectors.toList());

        for (MechanicImportDTO dto : mechanicImportDTOS) {
            stringBuilder.append(System.lineSeparator());

            if (this.mechanicsRepository.findFirstByFirstName(dto.getFirstName()).isPresent() ||
                    this.mechanicsRepository.findFirstByEmail(dto.getEmail()).isPresent() ||
//                    this.mechanicsRepository.findFirstByPhone(dto.getPhone()).isPresent() ||
                    !this.validationUtils.isValid(dto)) {
                stringBuilder.append(String.format(INVALID_FORMAT, MECHANIC));

            } else {
                Mechanic entity = this.modelMapper.map(dto, Mechanic.class);
                this.mechanicsRepository.save(entity);

                stringBuilder.append(String.format(SUCCESSFUL_FORMAT,
                        MECHANIC,
                        dto.getFirstName(),
                        dto.getLastName()));
            }

        }

        return stringBuilder.toString().trim();
    }
}
