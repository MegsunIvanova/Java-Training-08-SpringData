package softuni.exam.models.dto;

import softuni.exam.models.entity.Country;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ImportCityDTO {
    @Size(min = 2, max = 60)
    @NotNull
    private String cityName;

    @Size(min = 2)
    private String description;

    @Min(500)
    @NotNull
    private Integer population;

    private Long country;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Long getCountryId() {
        return country;
    }

    public void setCountryId(Long country) {
        this.country = country;
    }
}
