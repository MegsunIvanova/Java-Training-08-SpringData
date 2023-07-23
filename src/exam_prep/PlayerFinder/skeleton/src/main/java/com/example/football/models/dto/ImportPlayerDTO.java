package com.example.football.models.dto;

import com.example.football.models.entity.Position;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlayerDTO {

    @XmlElement(name = "first-name")
    @NotNull
    @Size(min = 2)
    private String firstName;

    @XmlElement(name = "last-name")
    @NotNull
    @Size(min = 2)
    private String lastName;

    @XmlElement
    @NotNull
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    @NotNull
    private String birthDate;

    @XmlElement
    @NotNull
    private String position;

    @XmlElement
    @NotNull
    private ImportPlayerTownDTO town;

    @XmlElement
    @NotNull
    private ImportPlayerTeamDTO team;

    @XmlElement
    @NotNull
    private ImportPlayerStatDTO stat;

    public ImportPlayerDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPosition() {
        return position;
    }

    public ImportPlayerTownDTO getTown() {
        return town;
    }

    public ImportPlayerTeamDTO getTeam() {
        return team;
    }

    public ImportPlayerStatDTO getStat() {
        return stat;
    }
}
