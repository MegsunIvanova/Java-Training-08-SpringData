package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureImportDTO;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.models.Constants.*;

@Service
public class PictureServiceImpl implements PictureService {

    private final String PICTURES_JSON_FILE = "src/main/resources/files/pictures.json";

    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson,
                              ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(PICTURES_JSON_FILE));
    }

    @Override
    public String importPictures() throws IOException {
        PictureImportDTO[] pictureDTOs =
                gson.fromJson(this.readFromFileContent(), PictureImportDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        for (PictureImportDTO dto : pictureDTOs) {

            Optional<Picture> pictureByPath = this.pictureRepository.findFirstByPath(dto.getPath());

            if (pictureByPath.isPresent() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, PICTURE));
                continue;
            }

            Picture entity = modelMapper.map(dto, Picture.class);

            this.pictureRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT_PICTURE,
                    entity.getSize()));
        }

        return String.join(System.lineSeparator(), resultMessages);

    }

    @Override
    public String exportPictures() {
        return this.pictureRepository.findAllBySizeGreaterThanOrderBySizeAsc(30000)
                .stream()
                .map(p -> String.format("%s - %.2f", p.getPath(), p.getSize()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
