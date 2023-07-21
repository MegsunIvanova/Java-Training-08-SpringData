package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;

@Service
public class PartsServiceImpl implements PartsService {

    private static final String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";

    private final PartsRepository partsRepository;
    private final Gson gson;

    private final ModelMapper modelMapper;

    private final ValidationUtils validationUtils;

    @Autowired
    public PartsServiceImpl(PartsRepository partsRepository, Gson gson, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.partsRepository = partsRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Path.of(PARTS_FILE_PATH));
    }

    @Override
    public String importParts() throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();

        List<PartImportDTO> partImportDTOS =
                Arrays.stream(this.gson.fromJson(readPartsFileContent(), PartImportDTO[].class))
                        .collect(Collectors.toList());

        for (PartImportDTO dto : partImportDTOS) {
            stringBuilder.append(System.lineSeparator());

            if (this.partsRepository.findFirstByPartName(dto.getPartName()).isPresent() ||
                    !this.validationUtils.isValid(dto)) {
                stringBuilder.append(String.format(INVALID_FORMAT, PART));

            } else {
                Part entity = this.modelMapper.map(dto, Part.class);
                this.partsRepository.save(entity);

                stringBuilder.append(String.format(SUCCESSFUL_FORMAT,
                        PART,
                        dto.getPartName() + " -",
                        dto.getPrice()));
            }

        }

        return stringBuilder.toString().trim();
    }
}
