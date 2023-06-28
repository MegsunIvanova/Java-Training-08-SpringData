package entities;

import entities.enums.CompetitionTypeValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "competition_type")
public class CompetitionType extends BaseEntity {

//    @Column
//    private String type;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private CompetitionTypeValue type;

}
