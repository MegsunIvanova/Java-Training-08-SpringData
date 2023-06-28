package entities;

import entities.enums.TeamShortName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table
public class Team extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String logo;

    @Column
    @Enumerated(EnumType.STRING)
    private TeamShortName initials;

    @ManyToOne
    @JoinColumn
    private Color primaryColor;

    @ManyToOne
    @JoinColumn
    private Color secondaryColor;

    @ManyToOne
    private Town town;

    @Column
    private BigDecimal budget;


}
