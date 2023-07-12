package bg.softuni.games_store;

import bg.softuni.games_store.exceptions.*;
import bg.softuni.games_store.services.ExecutorService;
import bg.softuni.games_store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final Scanner scanner;
    private final ExecutorService executorService;

    @Autowired
    public ConsoleRunner(Scanner scanner, UserService userService, ExecutorService executorService) {
        this.scanner = scanner;
        this.executorService = executorService;
    }

    @Override
    public void run(String... args) throws Exception {

        String command = scanner.nextLine();

        String result = "";

        while (!command.equals("exit")) {

            try {
                result = executorService.execute(command);
            } catch (ValidationException | UserNotLoggedInException | UserNotAdminException | NoSuchGameException |
                     EmptyCollectionException e) {
                result = e.getMessage();
            }

            System.out.println(result);

            command = scanner.nextLine();

        }
    }
}