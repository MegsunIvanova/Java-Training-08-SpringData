package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "bet_games")
public class BetGame implements Serializable {

    @Id
    @OneToOne
    @JoinColumn
    private Game game;

    @Id
    @OneToOne
    @JoinColumn
    private Bet bet;

    @OneToOne
    @JoinColumn
    private ResultPrediction resultPrediction;

}
