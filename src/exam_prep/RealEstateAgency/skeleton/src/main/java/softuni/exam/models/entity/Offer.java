package softuni.exam.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "published_on", nullable = false, columnDefinition = "DATE")
    private LocalDate publishedOn;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Agent agent;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Apartment apartment;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}