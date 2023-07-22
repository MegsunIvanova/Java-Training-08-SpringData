package softuni.exam.models.dto;

import softuni.exam.models.entity.DayOfWeek;

import javax.validation.Constraint;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastDTO {

    @XmlElement(name = "day_of_week")
    @NotNull
    private String dayOfWeek;

    @XmlElement(name = "max_temperature")
    @DecimalMin(value = "-20.0")
    @DecimalMax(value = "60.0")
    @NotNull
    private Double maxTemperature;

    @XmlElement(name = "min_temperature")
    @DecimalMin(value = "-50.0")
    @DecimalMax(value = "40.0")
    @NotNull
    private Double minTemperature;

    @XmlElement
    @NotNull
    private String sunrise;

    @XmlElement
    @NotNull
    private String sunset;

    @XmlElement(name = "city")
    private Long cityId;

    public ImportForecastDTO() {
    }

    public Long getCityId() {
        return cityId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
