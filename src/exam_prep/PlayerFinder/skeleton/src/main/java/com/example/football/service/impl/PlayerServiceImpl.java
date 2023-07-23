package com.example.football.service.impl;

import com.example.football.models.dto.ExportPlayerDTO;
import com.example.football.models.dto.ImportPlayerDTO;
import com.example.football.models.dto.ImportPlayerWrapperDTO;
import com.example.football.models.entity.*;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.football.models.Constants.*;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final String PLAYERS_XML_FILE = "src/main/resources/files/xml/players.xml";
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository, XmlParser xmlParser,
                             ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_XML_FILE));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {

        List<ImportPlayerDTO> playersDTOs =
                xmlParser.fromFile(PLAYERS_XML_FILE, ImportPlayerWrapperDTO.class).getPlayers();

        List<String> resultMessages = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (ImportPlayerDTO dto : playersDTOs) {

            Optional<Town> townByName = this.townRepository.findFirstByName(dto.getTown().getName());
            Optional<Team> teamByName = this.teamRepository.findFirstByName(dto.getTeam().getName());
            Optional<Stat> statById = this.statRepository.findById(dto.getStat().getId());
            Optional<Player> playerByEmail = this.playerRepository.findByEmail(dto.getEmail());

            Optional<Position> positionOptional;
            try {
                positionOptional = Optional.of(Position.valueOf(dto.getPosition()));
            } catch (IllegalArgumentException e) {
                positionOptional = Optional.empty();
            }

            LocalDate birthDate = LocalDate.parse(dto.getBirthDate(), formatter);

            if (playerByEmail.isPresent() ||
                    townByName.isEmpty() ||
                    teamByName.isEmpty() ||
                    statById.isEmpty() ||
                    positionOptional.isEmpty() ||
                    validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, PLAYER));
                continue;
            }


            Player entity = new Player(dto.getFirstName(),
                    dto.getLastName(),
                    dto.getEmail(),
                    birthDate,
                    positionOptional.get(),
                    statById.get(),
                    teamByName.get(),
                    townByName.get());

            this.playerRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    PLAYER,
                    entity.getFirstName() + " - " + entity.getLastName() + " - " + entity.getPosition().name()
            ));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }

    @Override
    public String exportBestPlayers() {
        List<Player> players =
                this.playerRepository
                        .findALlByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastName
                                (LocalDate.of(1995, 1, 1),
                                        LocalDate.of(2003, 1, 1));

        return players.stream()
                .map(player -> modelMapper.map(player, ExportPlayerDTO.class))
                .map(ExportPlayerDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
