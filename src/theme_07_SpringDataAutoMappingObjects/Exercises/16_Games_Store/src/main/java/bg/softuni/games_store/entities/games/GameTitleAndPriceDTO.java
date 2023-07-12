package bg.softuni.games_store.entities.games;

import java.math.BigDecimal;

public class GameTitleAndPriceDTO {
    private String title;

    private BigDecimal price;

    public GameTitleAndPriceDTO(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

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
}
