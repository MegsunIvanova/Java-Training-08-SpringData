package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportPlaneDTO;
import softuni.exam.models.dto.ImportTicketDTO;
import softuni.exam.models.dto.WrapperImportPlaneDTO;
import softuni.exam.models.dto.WrapperImportTicketDTO;
import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Plane;
import softuni.exam.models.entity.Ticket;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;
import static softuni.exam.models.Constants.PLANE;

@Service
public class TicketServiceImpl implements TicketService {
    private final String TICKETS_XML_FILE = "src/main/resources/files/xml/tickets.xml";

    private final TicketRepository ticketRepository;
    private final PlaneRepository planeRepository;
    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;

    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
//    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, PlaneRepository planeRepository,
                             PassengerRepository passengerRepository, TownRepository townRepository,
                             XmlParser xmlParser, ValidationUtil validationUtil) {
        this.ticketRepository = ticketRepository;
        this.planeRepository = planeRepository;
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_XML_FILE));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        List<ImportTicketDTO> ticketDTOs =
                this.xmlParser.fromFile(TICKETS_XML_FILE, WrapperImportTicketDTO.class).getTickets();

        List<String> resultMessages = new ArrayList<>();

        for (ImportTicketDTO dto : ticketDTOs) {
            Optional<Ticket> ticketBySerialNumber =
                    this.ticketRepository.findFirstBySerialNumber(dto.getSerialNumber());

            Optional<Town> fromTownByName = this.townRepository.findFirstByName(dto.getFromTown().getName());
            Optional<Town> toTownByName = this.townRepository.findFirstByName(dto.getToTown().getName());
            Optional<Passenger> passengerByEmail =
                    this.passengerRepository.findFirstByEmail(dto.getPassenger().getEmail());
            Optional<Plane> planeByRegNumber =
                    this.planeRepository.findFirstByRegisterNumber(dto.getPlane().getRegisterNumber());

            if (ticketBySerialNumber.isPresent() ||
//                    fromTownByName.isEmpty() || toTownByName.isEmpty() ||
//                    passengerByEmail.isEmpty() || planeByRegNumber.isEmpty() ||
                    validationUtil.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, TICKET));
                continue;
            }

            LocalDateTime takeOff = LocalDateTime.parse(dto.getTakeOff(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Ticket entity = new Ticket(dto.getSerialNumber(), dto.getPrice(), takeOff, fromTownByName.get(),
                    toTownByName.get(), passengerByEmail.get(), planeByRegNumber.get());

            this.ticketRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    TICKET,
                    entity.getFromTown().getName() + " - " + entity.getToTown().getName()));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }
}
