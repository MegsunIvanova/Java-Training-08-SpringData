package softuni.exam.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.exam.service.MechanicsService;
import softuni.exam.service.PartsService;
import softuni.exam.service.TasksService;
import softuni.exam.service.CarsService;

@Controller
public class HomeController extends BaseController {

    private final PartsService partsService;
    private final TasksService tasksService;
    private final CarsService carService;
    private final MechanicsService mechanicsService;

    @Autowired
    public HomeController(PartsService partsService, TasksService tasksService, CarsService carService, MechanicsService mechanicsService) {
        this.partsService = partsService;
        this.tasksService = tasksService;
        this.carService = carService;
        this.mechanicsService = mechanicsService;
    }


    @GetMapping("/")
    public ModelAndView index() {
        boolean areImported = this.partsService.areImported() &&
                this.carService.areImported() &&
                this.partsService.areImported() &&
                this.tasksService.areImported();

        return super.view("index", "areImported", areImported);
    }
}
