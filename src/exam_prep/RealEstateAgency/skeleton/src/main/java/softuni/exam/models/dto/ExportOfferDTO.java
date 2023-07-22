package softuni.exam.models.dto;

import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;

import java.math.BigDecimal;

public class ExportOfferDTO {

    private Long id;
    private BigDecimal price;

    private ExportApartmentDTO apartment;

    private ExportAgentDTO agent;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ExportApartmentDTO getApartment() {
        return apartment;
    }

    public void setApartment(ExportApartmentDTO apartment) {
        this.apartment = apartment;
    }

    public ExportAgentDTO getAgent() {
        return agent;
    }

    public void setAgent(ExportAgentDTO agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        return String.format("Agent %s %s with offer №%d:%n" +
                        " -Apartment area: %.2f%n" +
                        " --Town: %s%n" +
                        " ---Price: %.2f$",
                this.agent.getFirstName(),
                this.agent.getLastName(),
                this.id,
                this.apartment.getArea(),
                this.apartment.getTown().getTownName(),
                this.price);
    }
}
