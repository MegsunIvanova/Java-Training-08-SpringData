package bg.softuni.mvcdemo.dtos.companies;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompaniesImportWrapperDTO {

    @XmlElement(name = "company")
    private List<CompanyImportDTO> companies;

    public List<CompanyImportDTO> getCompanies() {
        return companies;
    }


}
