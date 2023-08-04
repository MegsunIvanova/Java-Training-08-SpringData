package bg.softuni.mvcdemo.services.impl;

import bg.softuni.mvcdemo.dtos.projects.ProjectBasicInfoDTO;
import bg.softuni.mvcdemo.dtos.projects.ProjectImportDTO;
import bg.softuni.mvcdemo.dtos.projects.ProjectsImportWrapperDTO;
import bg.softuni.mvcdemo.entities.Project;
import bg.softuni.mvcdemo.repositories.CompanyRepository;
import bg.softuni.mvcdemo.repositories.ProjectRepository;
import bg.softuni.mvcdemo.services.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, ModelMapper mapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String getXmlInfo() throws IOException {
        return String.join(
                System.lineSeparator(),
                Files.readAllLines(Path.of("src/main/resources/files/xmls/projects.xml")));
    }

    @Override
    public void importEntities() throws IOException, JAXBException {
        final String xmlInfo = this.getXmlInfo();
        final ByteArrayInputStream infoStream = new ByteArrayInputStream(xmlInfo.getBytes());

        final JAXBContext context = JAXBContext.newInstance(ProjectsImportWrapperDTO.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();

        final List<ProjectImportDTO> projectsDTOs =
                ((ProjectsImportWrapperDTO) unmarshaller.unmarshal(infoStream)).getProjects();

        List<Project> projects = projectsDTOs.stream()
                .map(pDTO -> mapper.map(pDTO, Project.class))
                .map(this::addCompany)
                .toList();

        this.projectRepository.saveAll(projects);
    }

    @Override
    public String getAllFinishedProjects() {
        return this.projectRepository
                .findAllByIsFinished(true)
                .stream()
                .map(entity -> mapper.map(entity, ProjectBasicInfoDTO.class))
                .map(ProjectBasicInfoDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private Project addCompany(Project project) {
        project.setCompany(this.companyRepository
                .findFirstByName(project.getCompany().getName())
                .get());

        return project;
    }
}
