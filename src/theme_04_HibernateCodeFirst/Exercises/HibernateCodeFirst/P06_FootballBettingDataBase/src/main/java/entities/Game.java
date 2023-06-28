package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter

@Entity
@Table
public class Game extends BaseEntity {

    @OneToOne
    @JoinColumn
    private Team homeTeam;

    @OneToOne
    @JoinColumn
    private Team awayTeam;

    @Column(name = "home_team_goals")
    private short homeGoals;

    @Column(name = "away_team_goals")
    private short awayGoals;

    @Column(name = "date_time")
    private LocalDateTime dateAndTime;

    @Column(name = "home_team_win_bet_rate")
    private BigDecimal homeTeamWinBetRate;

    @Column(name = "away_team_win_bet_rate")
    private BigDecimal awayTeamWinBetRate;

    @Column(name = "draw_game_bet_rate")
    private BigDecimal drawGameBetRate;

    @ManyToOne
    @JoinColumn
    private Round round;

    @ManyToOne
    @JoinColumn
    private Competition competition;

}
