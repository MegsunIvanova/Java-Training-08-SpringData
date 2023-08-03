package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportAstronomerDTO;
import softuni.exam.models.dto.WrapperImportAstronomerDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.util.Constants.*;

@Service
public class AstronomerServiceImpl implements AstronomerService {

    private final String FILE_XML_ASTRONOMERS = "src/main/resources/files/xml/astronomers.xml";
    private final AstronomerRepository astronomerRepository;
    private final StarRepository starRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarRepository starRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtils validationUtils) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(FILE_XML_ASTRONOMERS));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        List<ImportAstronomerDTO> astronomerDTOs =
                this.xmlParser.fromFile(FILE_XML_ASTRONOMERS, WrapperImportAstronomerDTO.class).getAstronomers();

        List<String> resultMessages = new ArrayList<>();

        for (ImportAstronomerDTO dto : astronomerDTOs) {

            Optional<Astronomer> astronomerByName =
                    this.astronomerRepository
                            .findFirstByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());

            Optional<Star> starById = this.starRepository.findById(dto.getObservingStar_id());

            if (astronomerByName.isPresent() || starById.isEmpty() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, ASTRONOMER));
                continue;
            }

            Astronomer entity = modelMapper.map(dto, Astronomer.class);
            entity.setObservingStar(starById.get());

            this.astronomerRepository.save(entity);

            String msg = String.format("%s %s - %.2f",
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getAverageObservationHours());

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    ASTRONOMER,
                    msg));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }
}
