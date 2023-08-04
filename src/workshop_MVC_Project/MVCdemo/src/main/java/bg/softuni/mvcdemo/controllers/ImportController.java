package bg.softuni.mvcdemo.controllers;

import bg.softuni.mvcdemo.services.CompanyService;
import bg.softuni.mvcdemo.services.EmployeeService;
import bg.softuni.mvcdemo.services.ProjectService;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class ImportController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/import/xml")
    public String mainView(Model model) {

        boolean[] importedStatuses =
                {
                        this.companyService.areImported(),
                        this.projectService.areImported(),
                        this.employeeService.areImported()
                };

        model.addAttribute("areImported", importedStatuses);

        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String getImportCompanies(Model model) throws IOException {

        model.addAttribute("companies", companyService.getXmlInfo());

        return "xml/import-companies";
    }


    @PostMapping("/import/companies")
    public String postCompanies() throws JAXBException, IOException {
        this.companyService.importEntities();

        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public String getImportProjects(Model model) throws IOException {

        model.addAttribute("projects", projectService.getXmlInfo());

        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String postProjects() throws JAXBException, IOException {
        this.projectService.importEntities();

        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public String getImportEmployees(Model model) throws IOException {

        model.addAttribute("employees", employeeService.getXmlInfo());

        return "xml/import-employees";
    }

    @PostMapping("/import/employees")
    public String postEmployees() throws JAXBException, IOException {
        this.employeeService.importEntities();

        return "redirect:/import/xml";
    }




}
