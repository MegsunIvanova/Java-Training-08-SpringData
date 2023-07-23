package com.example.football.service.impl;

import com.example.football.models.dto.ImportTownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
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


@Service
public class TownServiceImpl implements TownService {
    private final String TOWNS_JSON_FILE = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson,
                           ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_JSON_FILE));
    }

    @Override
    public String importTowns() throws IOException {
        ImportTownDTO[] townDTOs = gson.fromJson(this.readTownsFileContent(), ImportTownDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        for (ImportTownDTO dto : townDTOs) {
            Optional<Town> townByName = this.townRepository.findFirstByName(dto.getName());

            if (townByName.isPresent() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, TOWN));
                continue;
            }

            Town entity = modelMapper.map(dto, Town.class);

            this.townRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    TOWN,
                    entity.getName() + " - " +
                    entity.getPopulation()));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }
}
