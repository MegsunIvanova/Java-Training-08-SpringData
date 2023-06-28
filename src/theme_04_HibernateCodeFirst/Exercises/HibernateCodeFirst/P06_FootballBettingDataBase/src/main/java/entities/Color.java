package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table
public class Color extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

}
