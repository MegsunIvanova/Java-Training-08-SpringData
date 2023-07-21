package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskDTO;
import softuni.exam.models.dto.TaskImportDTO;
import softuni.exam.models.dto.TasksWrapperDTO;
import softuni.exam.models.entity.*;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.models.Constants.*;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";

    private final TasksRepository tasksRepository;
    private final CarsRepository carsRepository;
    private final PartsRepository partsRepository;
    private final MechanicsRepository mechanicsRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;

    @Autowired
    public TasksServiceImpl(TasksRepository tasksRepository, CarsRepository carsRepository,
                            PartsRepository partsRepository, MechanicsRepository mechanicsRepository,
                            ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils) {
        this.tasksRepository = tasksRepository;
        this.carsRepository = carsRepository;
        this.partsRepository = partsRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        final StringBuilder stringBuilder = new StringBuilder();

        List<TaskImportDTO> dTOs =
                this.xmlParser.fromFile(Path.of(TASKS_FILE_PATH).toFile(), TasksWrapperDTO.class)
                        .getTasks();

        for (TaskImportDTO dto : dTOs) {
            stringBuilder.append(System.lineSeparator());

            final Optional<Mechanic> mechanic =
                    this.mechanicsRepository.findFirstByFirstName(dto.getMechanic().getFirstName());
            final Optional<Car> car = this.carsRepository.findById(dto.getCar().getId());
            final Optional<Part> part = this.partsRepository.findById(dto.getPart().getId());

            if (mechanic.isEmpty() || car.isEmpty() || part.isEmpty() ||
                    !this.validationUtils.isValid(dto)) {
                stringBuilder.append(String.format(INVALID_FORMAT, TASK));

            } else {
                final Task entity = this.modelMapper.map(dto, Task.class);
                entity.setMechanic(mechanic.get());
                entity.setCar(car.get());
                entity.setPart(part.get());

                this.tasksRepository.save(entity);

                stringBuilder.append(String.format(SUCCESSFUL_FORMAT, TASK, dto.getPrice().setScale(2), "").trim());
            }
        }

        return stringBuilder.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return this.tasksRepository.findAllByCarCarTypeOrderByPriceDesc(CarType.coupe)
                .stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .map(TaskDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
