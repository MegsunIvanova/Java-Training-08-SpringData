import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressDTO implements Serializable {

    @XmlAttribute
    private int population;

    @XmlElement(name = "country")
    private String country;

    @XmlElement(name = "city-name")
    private String city;

    public AddressDTO() {
    }

    public AddressDTO(int population, String country, String city) {
        this.population = population;
        this.country = country;
        this.city = city;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "population=" + population +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
