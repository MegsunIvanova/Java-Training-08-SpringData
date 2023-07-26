package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportPassengerView;
import softuni.exam.models.dto.ImportPassengerDTO;
import softuni.exam.models.dto.ImportTownDTO;
import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;
import static softuni.exam.models.Constants.TOWN;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final String PASSENGERS_JSON_FILE = "src/main/resources/files/json/passengers.json";

    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository,
                                Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGERS_JSON_FILE));
    }

    @Override
    public String importPassengers() throws IOException {
        ImportPassengerDTO[] passengerDTOs =
                gson.fromJson(this.readPassengersFileContent(), ImportPassengerDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        for (ImportPassengerDTO dto : passengerDTOs) {


            Optional<Passenger> passengerByEmail = this.passengerRepository.findFirstByEmail(dto.getEmail());
            Optional<Town> townByName = this.townRepository.findFirstByName(dto.getTown());

            if (passengerByEmail.isPresent() ||
//                    townByName.isEmpty() ||
                    validationUtil.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, PASSENGER));
                continue;
            }

            Passenger entity = modelMapper.map(dto, Passenger.class);
            entity.setTown(townByName.get());

            this.passengerRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    PASSENGER,
                    entity.getLastName() + " - " + entity.getEmail()));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }

    @Override
    @Transactional
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        List<ExportPassengerView> passengers =
                this.passengerRepository.findAllByOrderByTicketsCountDescEmail();

        return passengers.stream()
                .map(ExportPassengerView::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
