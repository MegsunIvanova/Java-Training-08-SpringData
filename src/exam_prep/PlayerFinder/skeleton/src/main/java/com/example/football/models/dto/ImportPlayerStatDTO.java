package com.example.football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPlayerStatDTO {

    @XmlElement
    private Long id;

    public ImportPlayerStatDTO() {
    }

    public Long getId() {
        return id;
    }
}
