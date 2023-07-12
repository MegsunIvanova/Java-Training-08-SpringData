package bg.softuni.games_store.services;

import bg.softuni.games_store.entities.games.*;
import bg.softuni.games_store.entities.users.User;

import java.util.List;
import java.util.Optional;

public interface GameService {

    Game addGame(AddGameDTO addGameDTO);

    Optional<Game> findById(int id);

    Game updateGame(EditGameDTO editGameDTO);

    void delete(Game game);

    Game ensureGameExists(int id);

    List<GameTitleAndPriceDTO> getAllGames ();

    GameDetailsDTO getGameDetails(String title);

    Game findGameByTitle(String title);
}
