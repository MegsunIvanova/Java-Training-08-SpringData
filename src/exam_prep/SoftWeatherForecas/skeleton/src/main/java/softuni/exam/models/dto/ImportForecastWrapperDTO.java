package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastWrapperDTO {

    @XmlElement(name = "forecast")
    List<ImportForecastDTO> forecasts;

    public ImportForecastWrapperDTO() {
    }

    public List<ImportForecastDTO> getForecasts() {
        return forecasts;
    }
}
