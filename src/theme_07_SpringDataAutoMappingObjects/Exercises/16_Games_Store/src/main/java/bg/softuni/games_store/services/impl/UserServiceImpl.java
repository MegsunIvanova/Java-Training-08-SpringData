package bg.softuni.games_store.services.impl;

import bg.softuni.games_store.entities.games.Game;
import bg.softuni.games_store.entities.games.GameTitlesDTO;
import bg.softuni.games_store.entities.users.LoginDTO;
import bg.softuni.games_store.entities.users.RegisterDTO;
import bg.softuni.games_store.entities.users.User;
import bg.softuni.games_store.exceptions.EmptyCollectionException;
import bg.softuni.games_store.exceptions.UserNotAdminException;
import bg.softuni.games_store.exceptions.UserNotLoggedInException;
import bg.softuni.games_store.exceptions.ValidationException;
import bg.softuni.games_store.repositories.UserRepository;
import bg.softuni.games_store.services.GameService;
import bg.softuni.games_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private User currentUser;

    private Set<Game> shoppingCart;
    private final GameService gameService;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(GameService gameService, UserRepository userRepository, ModelMapper mapper) {
        this.gameService = gameService;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User register(RegisterDTO registerData) {
        long emailCount = this.userRepository.countUserByEmail(registerData.getEmail());

        if (emailCount != 0) {
            throw new ValidationException("Existing email!");
        }

        User toRegister = mapper.map(registerData, User.class);

        long userCount = this.userRepository.count();
        if (userCount == 0) {
            toRegister.setAdmin(true);
        }

        this.userRepository.save(toRegister);

        return toRegister;
    }

    @Override
    public Optional<User> login(LoginDTO loginData) {

        Optional<User> user = this.userRepository
                .findByEmailAndPassword(loginData.getEmail(), loginData.getPassword());

        user.ifPresent(value -> this.currentUser = value);

        this.shoppingCart = new HashSet<>();

        return user;
    }

    @Override
    public User getLoggedUser() {
        if (currentUser == null) {
            throw new UserNotLoggedInException();
        }

        return this.currentUser;
    }


    @Override
    public void logout() {
        this.currentUser = null;
        this.shoppingCart = null;
    }

    @Override
    public void ensureLoggedAdmin() {
        if (!this.getLoggedUser().isAdmin()) {
            throw new UserNotAdminException();
        }
    }

    @Override
    @Transactional
    public Set<Game> getUserGamesByUserId(Integer id) {
        return this.userRepository.getUserGames(id);
    }

    @Override
    @Transactional
    public List<GameTitlesDTO> getOwnedGames(Integer id) {
        Set<Game> ownedGames = getUserGamesByUserId(id);

        if (ownedGames.isEmpty()) {
            throw new EmptyCollectionException("User doesn't own any game!");
        }

        return this.userRepository.getUserGames(id).stream()
                .map(game -> this.mapper.map(game, GameTitlesDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addItemToShoppingCart(String title) {
        Game gameToAdd = this.gameService.findGameByTitle(title);

        if (shoppingCart.contains(gameToAdd)) {
            throw new ValidationException("This game has already been added to shopping cart!");
        }

        if (this.getUserGamesByUserId(this.currentUser.getId()).contains(gameToAdd)) {
            throw new ValidationException("This game has already bough!");
        }

        shoppingCart.add(gameToAdd);
    }

    @Override
    public void removeFromShoppingCart(String title) {
        Game gameToRemove = this.gameService.findGameByTitle(title);

        if (!shoppingCart.contains(gameToRemove)) {
            throw new ValidationException("This game is not in shopping cart!");
        }

        shoppingCart.remove(gameToRemove);
    }

    @Override
    @Transactional
    public List<GameTitlesDTO> buyItems() {
        if (shoppingCart.isEmpty()) {
            throw new ValidationException("Shopping card is empty!");
        }

        User user = this.userRepository.findById(this.currentUser.getId()).get();

        user.getGames().addAll(shoppingCart);

        this.userRepository.save(user);

        List<GameTitlesDTO> addedGames = shoppingCart.stream()
                .map(g -> mapper.map(g, GameTitlesDTO.class))
                .collect(Collectors.toList());

        this.shoppingCart.clear();

        return addedGames;
    }
}
