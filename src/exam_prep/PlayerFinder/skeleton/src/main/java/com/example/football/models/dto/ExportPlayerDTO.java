package com.example.football.models.dto;

import com.example.football.models.entity.Position;
import com.example.football.models.entity.Team;

public class ExportPlayerDTO {

    private String firstName;
    private String lastName;

    private Position position;

    private ExportTeamDTO team;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setTeam(ExportTeamDTO team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return String.format("Player - %s %s%n" +
                        "\tPosition - %s%n" +
                        "\tTeam - %s%n" +
                        "\tStadium - %s",
                this.firstName,
                this.lastName,
                this.position.name(),
                this.team.getName(),
                this.team.getStadiumName());
    }
}
