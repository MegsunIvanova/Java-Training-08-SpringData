package softuni.exam.service.impl;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportOfferDTO;
import softuni.exam.models.dto.ImportOfferDTO;
import softuni.exam.models.dto.ImportOfferWrapperDTO;
import softuni.exam.models.entity.*;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;

@Service
public class OfferServiceImpl implements OfferService {

    private final String OFFERS_XML_FILE = "src/main/resources/files/xml/offers.xml";
    private final OfferRepository offerRepository;
    private final ApartmentRepository apartmentRepository;
    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    private final Converter<String, LocalDate> stringLocalDateConverter;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ApartmentRepository apartmentRepository,
                            AgentRepository agentRepository, ModelMapper modelMapper,
                            ValidationUtils validationUtils, XmlParser xmlParser, Converter<String, LocalDate> stringLocalDateConverter) {
        this.offerRepository = offerRepository;
        this.apartmentRepository = apartmentRepository;
        this.agentRepository = agentRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.stringLocalDateConverter = stringLocalDateConverter;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_XML_FILE));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        List<ImportOfferDTO> dTOsFromXML =
                xmlParser.fromFile(OFFERS_XML_FILE, ImportOfferWrapperDTO.class)
                        .getOffers();

        List<String> result = new ArrayList<>();

        TypeMap<ImportOfferDTO, Offer> typeMap = this.modelMapper.createTypeMap(ImportOfferDTO.class, Offer.class)
                .addMappings(mapper -> mapper.using(this.stringLocalDateConverter)
                        .map(ImportOfferDTO::getPublishedOn, Offer::setPublishedOn));


        for (ImportOfferDTO dto : dTOsFromXML) {
            Optional<Agent> agentByFirstName = this.agentRepository.findFirstByFirstName(dto.getAgent().getFirstName());
            Optional<Apartment> apartmentById = this.apartmentRepository.findById(dto.getApartment().getId());

            if (agentByFirstName.isEmpty() ||
                    apartmentById.isEmpty() ||
                    validationUtils.isInvalid(dto)) {
                result.add(String.format(INVALID_DATA_FORMAT, OFFER));
                continue;
            }

            Offer entity = typeMap.map(dto);
            entity.setAgent(agentByFirstName.get());
            entity.setApartment(apartmentById.get());

            this.offerRepository.save(entity);

            result.add(String.format(SUCCESSFUL_DATA_FORMAT,
                    OFFER,
                    entity.getPrice().setScale(2),
                    ""));

        }

        return String.join(System.lineSeparator(), result);
    }

    @Override
    public String exportOffers() {
        List<Offer> offers = this.offerRepository
                .findAllByApartmentApartmentTypeOrderByApartmentAreaDescPriceAsc(
                        ApartmentType.three_rooms);


        return offers.stream()
                .map(offer -> modelMapper.map(offer, ExportOfferDTO.class))
                .map(ExportOfferDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
