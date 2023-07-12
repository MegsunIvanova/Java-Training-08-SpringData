package bg.softuni.games_store.entities.games;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GameDetailsDTO {
    private String title;
    private BigDecimal price;
    private String description;

    private LocalDate releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder
                .append("Title: ").append(this.title)
                .append(System.lineSeparator())
                .append("Price: ").append(String.format("%.2f", this.price))
                .append(System.lineSeparator())
                .append("Description: ").append(this.description)
                .append(System.lineSeparator())
                .append("Release date: ").append(this.releaseDate);

        return builder.toString();
    }
}
