package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlayerWrapperDTO {

    @XmlElement(name = "player")
    List<ImportPlayerDTO> players;

    public ImportPlayerWrapperDTO() {
    }

    public List<ImportPlayerDTO> getPlayers() {
        return players;
    }
}
