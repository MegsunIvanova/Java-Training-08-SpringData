package bg.softuni.cardealer.entities.customers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomerImportDto {

    private String name;

    private String birthDate;

    private boolean isYoungDriver;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public LocalDate getBirthDateAsDate () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(this.birthDate.substring(0, 10), formatter);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate.substring(0,10);
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
