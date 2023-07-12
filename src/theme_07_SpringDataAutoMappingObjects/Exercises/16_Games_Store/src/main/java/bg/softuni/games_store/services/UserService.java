package bg.softuni.games_store.services;

import bg.softuni.games_store.entities.games.Game;
import bg.softuni.games_store.entities.games.GameTitlesDTO;
import bg.softuni.games_store.entities.users.LoginDTO;
import bg.softuni.games_store.entities.users.RegisterDTO;
import bg.softuni.games_store.entities.users.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    User register(RegisterDTO registerData);

    Optional<User> login(LoginDTO loginData);

    User getLoggedUser();

    void logout();

    void ensureLoggedAdmin();

    @Transactional
    Set<Game> getUserGamesByUserId(Integer id);

    List<GameTitlesDTO> getOwnedGames(Integer id);

    void addItemToShoppingCart(String title);

    void removeFromShoppingCart(String title);

    List<GameTitlesDTO> buyItems();
}
