package bg.softuni.mvcdemo.dtos.employees;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeesImportWrapperDTO {

    @XmlElement(name = "employee")
    private List<EmployeeImportDTO> employees;

    public List<EmployeeImportDTO> getEmployees() {
        return employees;
    }
}
