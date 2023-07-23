package com.example.football.models.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Position position;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Stat stat;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Town town;


    public Player() {
    }

    public Player(String firstName, String lastName,
                  String email, LocalDate birthDate,
                  Position position, Stat stat, Team team,
                  Town town) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.position = position;
        this.stat = stat;
        this.team = team;
        this.town = town;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
