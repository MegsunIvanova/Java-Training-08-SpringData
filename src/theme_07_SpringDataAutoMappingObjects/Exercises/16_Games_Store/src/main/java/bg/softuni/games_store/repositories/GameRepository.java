package bg.softuni.games_store.repositories;

import bg.softuni.games_store.entities.games.Game;
import bg.softuni.games_store.entities.games.GameTitleAndPriceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findById(int id);

    @Query("SELECT new bg.softuni.games_store.entities.games.GameTitleAndPriceDTO " +
            "(g.title, g.price)" +
            " FROM Game g")
    List<GameTitleAndPriceDTO> getAllGamesWithTitleAndPrice();

    Optional<Game> findByTitle(String title);


}
