package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportPlaneDTO;
import softuni.exam.models.dto.ImportTownDTO;
import softuni.exam.models.dto.WrapperImportPlaneDTO;
import softuni.exam.models.entity.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final String PLANES_XML_FILE = "src/main/resources/files/xml/planes.xml";

    private final PlaneRepository planeRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository, XmlParser xmlParser,
                            ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.planeRepository = planeRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANES_XML_FILE));
    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {
        List<ImportPlaneDTO> planeDTOs =
                this.xmlParser.fromFile(PLANES_XML_FILE, WrapperImportPlaneDTO.class).getPlanes();

        List<String> resultMessages = new ArrayList<>();

        for (ImportPlaneDTO dto : planeDTOs) {

            Optional<Plane> planeByRegNumber =
                    this.planeRepository.findFirstByRegisterNumber(dto.getRegisterNumber());

            if (planeByRegNumber.isPresent() || validationUtil.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, PLANE));
                continue;
            }

            Plane entity = modelMapper.map(dto, Plane.class);

            this.planeRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    PLANE,
                    entity.getRegisterNumber()));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }
}
