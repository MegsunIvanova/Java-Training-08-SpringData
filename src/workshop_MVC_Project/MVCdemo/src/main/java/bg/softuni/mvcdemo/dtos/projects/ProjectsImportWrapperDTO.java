package bg.softuni.mvcdemo.dtos.projects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectsImportWrapperDTO {

    @XmlElement(name = "project")
    List<ProjectImportDTO> projects;

    public List<ProjectImportDTO> getProjects() {
        return projects;
    }
}
