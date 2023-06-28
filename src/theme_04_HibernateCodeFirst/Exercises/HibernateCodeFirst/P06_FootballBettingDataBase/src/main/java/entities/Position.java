package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table
public class Position {

    @Id
    @Column (length = 2, nullable = false, unique = true)
    private String id;

    @Column
    private String description;
}
