package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table
public class Continent extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

//    @ManyToMany(mappedBy = "continents", targetEntity = Country.class)
//    private Set<Country> countries;
}
