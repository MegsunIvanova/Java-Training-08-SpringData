package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.dto.ImportStatWrapperDTO;
import com.example.football.models.dto.ImportTeamDTO;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtils;
import com.example.football.util.XmlParser;
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

import static com.example.football.models.Constants.*;
import static com.example.football.models.Constants.TEAM;

@Service
public class StatServiceImpl implements StatService {
    private final String STATS_XML_FILE = "src/main/resources/files/xml/stats.xml";
    private final StatRepository statRepository;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public StatServiceImpl(StatRepository statRepository, XmlParser xmlParser,
                           ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.statRepository = statRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_XML_FILE));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        List<ImportStatDTO> statDTOs =
                xmlParser.fromFile(STATS_XML_FILE, ImportStatWrapperDTO.class).getStats();

        List<String> resultMessages = new ArrayList<>();

        for (ImportStatDTO dto : statDTOs) {

            Optional<Stat> statByParams = this.statRepository
                    .findFirstByShootingAndPassingAndEndurance(
                            dto.getShooting(),
                            dto.getPassing(),
                            dto.getEndurance());


            if (statByParams.isPresent() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, STAT));
                continue;
            }

            Stat entity = modelMapper.map(dto, Stat.class);

            this.statRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT,
                    STAT,
                    String.format(STAT_INFO_FORMAT,
                            entity.getPassing(),
                            entity.getShooting(),
                            entity.getEndurance())
            ));
        }

        return String.join(System.lineSeparator(), resultMessages);

    }
}
