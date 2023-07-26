package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class WrapperImportTicketDTO {

    @XmlElement(name = "ticket")
    private List<ImportTicketDTO> tickets;

    public WrapperImportTicketDTO() {
    }

    public List<ImportTicketDTO> getTickets() {
        return tickets;
    }
}
