package bg.softuni.games_store.entities.games;

import bg.softuni.games_store.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface GameDTO {
    int MIN_TITLE_LENGTH = 3;
   int MAX_TITLE_LENGTH = 100;


    String getTitle();

    BigDecimal getPrice();

    Float getSize();

    String getTrailer();

    String getThumbnailUrl();

    String getDescription();

    LocalDate getReleaseDate();

    default void validate() {
        List<String> exceptionMessages = new ArrayList<>();

        boolean firstUppercase = Character.isUpperCase(this.getTitle().toCharArray()[0]);
        boolean titleLength = this.getTitle().length() >= MIN_TITLE_LENGTH
                && this.getTitle().length() <= MAX_TITLE_LENGTH;

        if (!(firstUppercase && titleLength)) {
            exceptionMessages.add("Incorrect Title!");
        }

        if ((this.getPrice().compareTo(BigDecimal.ZERO) <= 0)) {
            exceptionMessages.add("Incorrect Price!");
        }

        if (this.getSize() <= 0) {
            exceptionMessages.add("Incorrect Size!");
        }

        if (this.getTrailer().length() != 11) {
            exceptionMessages.add("Incorrect Trailer ID!");
        }

        boolean startWith = this.getThumbnailUrl().startsWith("http://")
                || this.getThumbnailUrl().startsWith("https://");

        if (!startWith) {
            exceptionMessages.add("Incorrect Thumbnail URL!");
        }

        if (this.getDescription().length() < 20) {
            exceptionMessages.add("Incorrect Description!");
        }

        if (exceptionMessages.size() > 0) {
            throw new ValidationException(String.join(
                    System.lineSeparator(), exceptionMessages));
        }
    }


}
