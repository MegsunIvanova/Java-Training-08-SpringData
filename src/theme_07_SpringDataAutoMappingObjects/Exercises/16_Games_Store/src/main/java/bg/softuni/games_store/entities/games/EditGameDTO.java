package bg.softuni.games_store.entities.games;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class EditGameDTO implements GameDTO {

    private Integer id;

    private String title;

    private BigDecimal price;
    private Float size;

    private String trailer;

    private String thumbnailUrl;

    private String description;

    private LocalDate releaseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    @Override
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void updateFields(Map<String, String> forUpdate) {
        for (String key : forUpdate.keySet()) {
            String value = forUpdate.get(key);

            if (key.equals("title")) {
                this.title = value;
            } else if (key.equals("price")) {
                this.price = new BigDecimal(value);
            } else if (key.equals("size")) {
                this.size = Float.parseFloat(value);
            } else if (key.equals("trailer")) {
                this.trailer = value;
            } else if (key.equals("thumbnailUrl")) {
                this.thumbnailUrl = value;
            } else if (key.equals("description")) {
                this.description = value;
            } else if (key.equals("releaseDate")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                this.releaseDate = LocalDate.parse(value, formatter);
            }
        }

        validate();
    }
}