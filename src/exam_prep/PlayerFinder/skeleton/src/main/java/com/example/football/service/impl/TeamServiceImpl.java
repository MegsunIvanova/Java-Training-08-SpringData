package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamDTO;
import com.example.football.models.dto.ImportTownDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtils;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.football.models.Constants.*;
import static com.example.football.models.Constants.TOWN;

@Service
public class TeamServiceImpl implements TeamService {

    private final String TEAMS_JSON_FILE = "src/main/resources/files/json/teams.json";
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository,
                           Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_JSON_FILE));
    }

    @Override
    public String importTeams() throws IOException {
        ImportTeamDTO[] townDTOs = gson.fromJson(this.readTeamsFileContent(), ImportTeamDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        for (ImportTeamDTO dto : townDTOs) {
            Optional<Town> townByName = this.townRepository.findFirstByName(dto.getTownName());
            Optional<Team> teamByName = this.teamRepository.findFirstByName(dto.getName());

            if (townByName.isEmpty() || teamByName.isPresent() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, TEAM));
                continue;
            }

            Team entity = modelMapper.map(dto, Team.class);
            entity.setTown(townByName.get());

            this.teamRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    TEAM,
                    entity.getName() + " - " +
                    entity.getFanBase()));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }
}
