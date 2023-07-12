package bg.softuni.games_store.entities.games;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddGameDTO implements GameDTO{

    private String title;

    private BigDecimal price;
    private Float size;

    private String trailer;

    private String thumbnailUrl;

    private String description;

    private LocalDate releaseDate;

    public AddGameDTO(String[] commandParts) {

        this.title = commandParts[1];
        this.price = new BigDecimal(commandParts[2]);
        this.size = Float.parseFloat(commandParts[3]);
        this.trailer = commandParts[4];
        this.thumbnailUrl = commandParts[5];
        this.description = commandParts[6];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.releaseDate = LocalDate.parse(commandParts[7], formatter);
        this.validate();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public Float getSize() {
        return size;
    }

    @Override
    public String getTrailer() {
        return trailer;
    }

    @Override
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

}
