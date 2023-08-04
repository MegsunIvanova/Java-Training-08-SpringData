package bg.softuni.mvcdemo.services.impl;

import bg.softuni.mvcdemo.dtos.employees.EmployeeBasicInfoDTO;
import bg.softuni.mvcdemo.dtos.employees.EmployeeImportDTO;
import bg.softuni.mvcdemo.dtos.employees.EmployeesImportWrapperDTO;
import bg.softuni.mvcdemo.dtos.projects.ProjectImportDTO;
import bg.softuni.mvcdemo.dtos.projects.ProjectsImportWrapperDTO;
import bg.softuni.mvcdemo.entities.Employee;
import bg.softuni.mvcdemo.entities.Project;
import bg.softuni.mvcdemo.repositories.EmployeeRepository;
import bg.softuni.mvcdemo.repositories.ProjectRepository;
import bg.softuni.mvcdemo.services.EmployeeService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper mapper;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String getXmlInfo() throws IOException {
        return String.join(
                System.lineSeparator(),
                Files.readAllLines(Path.of("src/main/resources/files/xmls/employees.xml")));
    }

    @Override
    public void importEntities() throws IOException, JAXBException {
        final String xmlInfo = this.getXmlInfo();
        final ByteArrayInputStream infoStream = new ByteArrayInputStream(xmlInfo.getBytes());

        final JAXBContext context = JAXBContext.newInstance(EmployeesImportWrapperDTO.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        final List<EmployeeImportDTO> employeesDTOs =
                ((EmployeesImportWrapperDTO) unmarshaller.unmarshal(infoStream)).getEmployees();

        List<Employee> employees = employeesDTOs.stream()
                .map(eDTO -> mapper.map(eDTO, Employee.class))
                .map(this::addProject)
                .toList();

        this.employeeRepository.saveAll(employees);
    }

    @Override
    public String getAllEmployeesWithAgeAbove25() {
        return this.employeeRepository.findAllByAgeGreaterThan(25)
                .stream()
                .map(entity -> mapper.map(entity, EmployeeBasicInfoDTO.class))
                .map(EmployeeBasicInfoDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private Employee addProject(Employee employee) {

        Project project = employee.getProject();

        employee.setProject(this.projectRepository
                .findFirstByName(project.getName())
                .get());

        return employee;
    }
}
