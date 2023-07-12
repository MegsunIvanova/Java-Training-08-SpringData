package bg.softuni.games_store.repositories;

import bg.softuni.games_store.entities.games.Game;
import bg.softuni.games_store.entities.games.GameTitlesDTO;
import bg.softuni.games_store.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

    int countUserByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    @Query("SELECT u.games FROM User u WHERE u.id = :id")
    Set<Game> getUserGames(Integer id);

}
