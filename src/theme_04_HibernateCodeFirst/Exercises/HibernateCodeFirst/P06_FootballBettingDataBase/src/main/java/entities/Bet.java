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
public class Bet extends BaseEntity{

    @Column(name = "bet_money")
    private BigDecimal betMoney;

    @Column(name = "date_time")
    private LocalDateTime dateAndTime;

    @ManyToOne
    private User user;
}
