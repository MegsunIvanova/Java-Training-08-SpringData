package exam.model.dto;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportShopDTO {

    @XmlElement
    @NotNull
    @Size(min = 4)
    private String address;

    @XmlElement(name = "employee-count")
    @NotNull
    @Min(1)
    @Max(50)
    private Integer employeeCount;

    @XmlElement
    @NotNull
    @DecimalMin(value = "20000")
    private BigDecimal income;

    @XmlElement
    @NotNull
    @Size(min = 4)
    private String name;

    @XmlElement(name = "shop-area")
    @NotNull
    @Min(150)
    private Integer shopArea;

    @XmlElement
    private ImportShopTownDTO town;

    public ImportShopDTO() {
    }

    public String getAddress() {
        return address;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }

    public Integer getShopArea() {
        return shopArea;
    }

    public ImportShopTownDTO getTown() {
        return town;
    }
}
