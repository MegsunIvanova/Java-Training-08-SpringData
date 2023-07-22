package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportAgentDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.models.Constants.*;

@Service
public class AgentServiceImpl implements AgentService {

    private final String AGENTS_JSON_FILE = "src/main/resources/files/json/agents.json";
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;

    @Autowired
    public AgentServiceImpl(Gson gson, ModelMapper modelMapper,
                            ValidationUtils validationUtils, AgentRepository agentRepository,
                            TownRepository townRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENTS_JSON_FILE));
    }

    @Override
    public String importAgents() throws IOException {
        ImportAgentDTO[] dTOsFromJson = gson.fromJson(this.readAgentsFromFile(), ImportAgentDTO[].class);

        List<String> result = new ArrayList<>();

        for (ImportAgentDTO dto : dTOsFromJson) {
            Optional<Agent> agentByFirstName = this.agentRepository.findFirstByFirstName(dto.getFirstName());
            Optional<Agent> agentByEmail = this.agentRepository.findFirstByEmail(dto.getEmail());
            Optional<Town> townByName = this.townRepository.findFirstByTownName(dto.getTown());


            if (agentByFirstName.isPresent() ||
                    agentByEmail.isPresent() ||
                    townByName.isEmpty() ||
                    validationUtils.isInvalid(dto)) {
                result.add(String.format(INVALID_DATA_FORMAT, AGENT));
                continue;
            }

            Agent entity = modelMapper.map(dto, Agent.class);
            entity.setTown(townByName.get());

            this.agentRepository.save(entity);

            result.add(String.format(SUCCESSFUL_DATA_FORMAT,
                    AGENT,
                    entity.getFirstName(),
                    entity.getLastName()));

        }

        return String.join(System.lineSeparator(), result);
    }
}
