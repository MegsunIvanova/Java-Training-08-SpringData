package softuni.exam.models.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportOfferDTO {

    @XmlElement(name = "price")
    @NotNull
    @Positive
    private BigDecimal price;


    @XmlElement(name = "agent")
    @NotNull
    private ImportOfferAgentDTO agent;

    @XmlElement(name = "apartment")
    @NotNull
    private ImportOfferApartmentDTO apartment;

    @XmlElement
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String publishedOn;

    public ImportOfferDTO() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ImportOfferAgentDTO getAgent() {
        return agent;
    }

    public void setAgent(ImportOfferAgentDTO agent) {
        this.agent = agent;
    }

    public ImportOfferApartmentDTO getApartment() {
        return apartment;
    }

    public void setApartment(ImportOfferApartmentDTO apartment) {
        this.apartment = apartment;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }
}
