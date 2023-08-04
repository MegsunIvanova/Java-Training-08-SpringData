package bg.softuni.mvcdemo.dtos.projects;

import bg.softuni.mvcdemo.dtos.companies.CompanyImportDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectImportDTO {

    @XmlElement
    private String name;

    @XmlElement
    private String description;

    @XmlElement(name = "start-date")
    private String startDate;

    @XmlElement(name = "is-finished")
    private boolean isFinished;

    @XmlElement
    private BigDecimal payment;

    @XmlElement
    private CompanyImportDTO company;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public CompanyImportDTO getCompany() {
        return company;
    }
}
