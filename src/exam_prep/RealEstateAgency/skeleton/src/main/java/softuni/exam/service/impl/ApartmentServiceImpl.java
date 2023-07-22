package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportApartmentDTO;
import softuni.exam.models.dto.ImportApartmentWrapperDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;
import static softuni.exam.models.Constants.AGENT;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final String APARTMENTS_XML_FILE = "src/main/resources/files/xml/apartments.xml";
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository,
                                ModelMapper modelMapper,
                                ValidationUtils validationUtils, XmlParser xmlParser) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_XML_FILE));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        List<ImportApartmentDTO> dTOsFromXML =
                xmlParser.fromFile(APARTMENTS_XML_FILE, ImportApartmentWrapperDTO.class)
                        .getApartments();

        List<String> result = new ArrayList<>();

        for (ImportApartmentDTO dto : dTOsFromXML) {
            Optional<Apartment> apartmentByTownAndArea =
                    this.apartmentRepository.findByTownTownNameAndArea(dto.getTownName(), dto.getArea());

            Optional<Town> townByName = this.townRepository.findFirstByTownName(dto.getTownName());

            Optional<ApartmentType> apartmentType;

            try {
                apartmentType = Optional.of(ApartmentType.valueOf(dto.getApartmentType()));
            } catch (IllegalArgumentException e) {
                apartmentType = Optional.empty();
            }

            if (apartmentByTownAndArea.isPresent() ||
                    townByName.isEmpty() ||
                    apartmentType.isEmpty() ||
                    validationUtils.isInvalid(dto)) {
                result.add(String.format(INVALID_DATA_FORMAT, APARTMENT));
                continue;
            }

            Apartment entity = new Apartment(apartmentType.get(), dto.getArea(), townByName.get());

            this.apartmentRepository.save(entity);

            result.add(String.format(SUCCESSFUL_DATA_FORMAT,
                    APARTMENT,
                    entity.getApartmentType().name() + " -",
                    entity.getArea()));

        }

        return String.join(System.lineSeparator(), result);
    }
}
