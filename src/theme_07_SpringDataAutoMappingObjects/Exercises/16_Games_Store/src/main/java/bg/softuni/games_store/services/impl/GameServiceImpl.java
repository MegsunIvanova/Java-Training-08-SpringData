package bg.softuni.games_store.services.impl;

import bg.softuni.games_store.entities.games.*;
import bg.softuni.games_store.exceptions.NoSuchGameException;
import bg.softuni.games_store.repositories.GameRepository;
import bg.softuni.games_store.services.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final ModelMapper modelMapper;
    private final GameRepository gameRepository;

    public GameServiceImpl(ModelMapper modelMapper, GameRepository gameRepository) {
        this.modelMapper = modelMapper;
        this.gameRepository = gameRepository;
    }

    @Override
    public Game addGame(AddGameDTO addGameDTO) {
        Game gameToAdd = modelMapper.map(addGameDTO, Game.class);

        return this.gameRepository.save(gameToAdd);
    }

    @Override
    public Optional<Game> findById(int id) {
        return this.gameRepository.findById(id);
    }

    @Override
    public Game updateGame(EditGameDTO editGameDTO) {
        Game updatedGame = this.modelMapper.map(editGameDTO, Game.class);

        return this.gameRepository.save(updatedGame);
    }

    @Override
    public void delete(Game gameToDelete) {
        this.gameRepository.delete(gameToDelete);
    }

    @Override
    public Game ensureGameExists(int id) {

        Optional<Game> gameToEdit = this.findById(id);

        if (gameToEdit.isEmpty()) {
            throw new NoSuchGameException();
        }

        return gameToEdit.get();
    }

    @Override
    public List<GameTitleAndPriceDTO> getAllGames() {
        return this.gameRepository.getAllGamesWithTitleAndPrice();
    }

    @Override
    public GameDetailsDTO getGameDetails(String title) {
        Game game = this.findGameByTitle(title);

        return modelMapper.map(game, GameDetailsDTO.class);
    }


    @Override
    public Game findGameByTitle(String title) {
        Optional<Game> game = this.gameRepository.findByTitle(title);

        if (game.isEmpty()) {
            throw new NoSuchGameException();
        }

        return game.get();
    }

}
