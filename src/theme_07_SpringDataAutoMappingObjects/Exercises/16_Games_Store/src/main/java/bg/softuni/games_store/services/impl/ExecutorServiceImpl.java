package bg.softuni.games_store.services.impl;

import bg.softuni.games_store.entities.games.*;
import bg.softuni.games_store.entities.users.LoginDTO;
import bg.softuni.games_store.entities.users.RegisterDTO;
import bg.softuni.games_store.entities.users.User;
import bg.softuni.games_store.exceptions.NoSuchGameException;
import bg.softuni.games_store.exceptions.UserNotAdminException;
import bg.softuni.games_store.services.ExecutorService;
import bg.softuni.games_store.services.GameService;
import bg.softuni.games_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExecutorServiceImpl implements ExecutorService {

    private final UserService userService;
    private final GameService gameService;

    private final ModelMapper modelMapper;

    @Autowired
    public ExecutorServiceImpl(UserService userService, GameService gameService, ModelMapper modelMapper) {
        this.userService = userService;
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String execute(String commandLine) {
        String[] commandParts = commandLine.split("\\|");

        String commandName = commandParts[0];

        String commandOutput = switch (commandName) {
            case REGISTER_USER_COMMAND -> registerUser(commandParts);
            case LOGIN_USER_COMMAND -> loginUser(commandParts);
            case LOGOUT_USER_COMMAND -> logoutUser();
            case ADD_GAME_COMMAND -> addGame(commandParts);
            case EDIT_GAME_COMMAND -> editGame(commandParts);
            case DELETE_GAME_COMMAND -> deleteGame(commandParts);
            case ALL_GAMES_COMMAND -> allGames();
            case DETAIL_GAME_COMMAND -> detailsGame(commandParts);
            case OWNED_GAMES_COMMAND -> getOwnedGames();
            case ADD_ITEM_COMMAND -> addItemToShoppingCart(commandParts);
            case REMOVE_ITEM_COMMAND -> removeFromShoppingCart(commandParts);
            case BUY_ITEM_COMMAND -> buyItems();

            default -> "unknown command";
        };

        return commandOutput;

    }

    private String buyItems() {
        this.userService.getLoggedUser();
        List<GameTitlesDTO> boughtGames = this.userService.buyItems();
        StringBuilder out = new StringBuilder();

        out.append("Successfully bought games:").append(System.lineSeparator())
                .append(String.join(System.lineSeparator(),
                        boughtGames.stream()
                                .map(g -> " -" + g.toString())
                                .collect(Collectors.toList())));

        return out.toString();
    }

    private String removeFromShoppingCart(String[] commandParts) {
        this.userService.getLoggedUser();

        this.userService.removeFromShoppingCart(commandParts[1]);

        return String.format("%s removed from cart.", commandParts[1]);
    }

    private String addItemToShoppingCart(String[] commandParts) {
        this.userService.getLoggedUser();

        this.userService.addItemToShoppingCart(commandParts[1]);

        return String.format("%s added to cart.", commandParts[1]);
    }

    private String getOwnedGames() {
        User loggedUser = this.userService.getLoggedUser();

        List<GameTitlesDTO> ownedGames = this.userService.getOwnedGames(loggedUser.getId());

        return ownedGames
                .stream()
                .map(GameTitlesDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String detailsGame(String[] commandParts) {
        this.userService.getLoggedUser();
        GameDetailsDTO gameDetails = this.gameService.getGameDetails(commandParts[1]);

        return gameDetails.toString();

    }

    private String allGames() {
        User loggedUser = this.userService.getLoggedUser();

        List<GameTitleAndPriceDTO> allGames = this.gameService.getAllGames();

        return allGames.stream().map(g -> String.format("%s %.2f", g.getTitle(), g.getPrice()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String deleteGame(String[] commandParts) {
        this.userService.ensureLoggedAdmin();

        int id = Integer.parseInt(commandParts[1]);

        Game gameToDelete = this.gameService.ensureGameExists(id);

        this.gameService.delete(gameToDelete);

        return String.format("Deleted %s", gameToDelete.getTitle());
    }

    private String editGame(String[] commandParts) {
        this.userService.ensureLoggedAdmin();

        int id = Integer.parseInt(commandParts[1]);

        Game gameToEdit = this.gameService.ensureGameExists(id);

        Map<String, String> forUpdate = new HashMap<>();

        for (int i = 2; i < commandParts.length; i++) {
            String[] arguments = commandParts[i].split("=");
            forUpdate.put(arguments[0], arguments[1]);
        }

        EditGameDTO editGameDTO = this.modelMapper.map(gameToEdit, EditGameDTO.class);

        editGameDTO.updateFields(forUpdate);

        Game updatedGame = this.gameService.updateGame(editGameDTO);

        return String.format("Edited %s", updatedGame.getTitle());
    }

    private String addGame(String[] commandParts) {
        this.userService.ensureLoggedAdmin();

        AddGameDTO addGameDTO = new AddGameDTO(commandParts);

        Game game = gameService.addGame(addGameDTO);

        return String.format("Added %s", game.getTitle());
    }

//    private void ensureLoggedAdmin() {
//        User loggedUser = this.userService.getLoggedUser();
//
//        if (!loggedUser.isAdmin()) {
//            throw new UserNotAdminException();
//        }
//    }

//    private Game ensureGameExists(int id) {
//
//        Optional<Game> gameToEdit = gameService.findById(id);
//
//        if (gameToEdit.isEmpty()) {
//            throw new NoSuchGameException();
//        }
//
//        return gameToEdit.get();
//    }

    private String logoutUser() {
        User loggedtUser = this.userService.getLoggedUser();

        this.userService.logout();

        return String.format("User %s successfully logged out",
                loggedtUser.getFullName());
    }

    private String loginUser(String[] commandParts) {
        LoginDTO loginData = new LoginDTO(commandParts);

        Optional<User> user = userService.login(loginData);

        if (user.isPresent()) {
            return String.format("Successfully logged in %s", user.get().getFullName());
        } else {
            return "Incorrect username / password";
        }
    }

    private String registerUser(String[] commandParts) {

        RegisterDTO registerData = new RegisterDTO(commandParts);

        User user = userService.register(registerData);

        return String.format("%s was registered", user.getFullName());
    }
}
