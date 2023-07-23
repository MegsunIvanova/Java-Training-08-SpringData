package exam.service.impl;

import exam.model.dto.ImportTownDTO;
import exam.model.dto.ImportTownWrapperDTO;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtils;
import exam.util.XMLParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static exam.model.Constant.*;

@Service
public class TownServiceImpl implements TownService {
    private final String TOWN_XML_FILE = "src/main/resources/files/xml/towns.xml";
    private final TownRepository townRepository;
    private final XMLParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, XMLParser xmlParser,
                           ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_XML_FILE));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        List<ImportTownDTO> townDTOs =
                this.xmlParser.fromFile(TOWN_XML_FILE, ImportTownWrapperDTO.class).getTowns();

        List<String> resultMessages = new ArrayList<>();

        for (ImportTownDTO dto : townDTOs) {

            Optional<Town> townByName = this.townRepository.findFirstByName(dto.getName());

            if (townByName.isPresent() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, TOWN));
                continue;
            }

            Town entity = modelMapper.map(dto, Town.class);

            this.townRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT_TOWN,
                    TOWN,
                    entity.getName()));
        }

        return String.join(System.lineSeparator(), resultMessages);

    }
}
