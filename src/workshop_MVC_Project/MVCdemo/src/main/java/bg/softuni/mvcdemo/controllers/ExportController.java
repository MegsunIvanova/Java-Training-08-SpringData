package bg.softuni.mvcdemo.controllers;

import bg.softuni.mvcdemo.services.EmployeeService;
import bg.softuni.mvcdemo.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExportController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/export/export-project-if-finished")
    public String getAllFinishedProjects (Model model) {
        String allFinishedProjects = this.projectService.getAllFinishedProjects();

        model.addAttribute("projectsIfFinished", allFinishedProjects);

        return "/export/export-project-if-finished";

    }


    @GetMapping("/export/export-employees-with-age")
    public String getAllEmployeesWithAgeAbove25 (Model model) {
        String allEmployeesWithAgeAbove25 = this.employeeService.getAllEmployeesWithAgeAbove25();

        model.addAttribute("employeesAbove", allEmployeesWithAgeAbove25);

        return "/export/export-employees-with-age";

    }

}
