package bg.softuni.mvcdemo.services.impl;

import bg.softuni.mvcdemo.dtos.companies.CompaniesImportWrapperDTO;
import bg.softuni.mvcdemo.dtos.companies.CompanyImportDTO;
import bg.softuni.mvcdemo.entities.Company;
import bg.softuni.mvcdemo.repositories.CompanyRepository;
import bg.softuni.mvcdemo.services.CompanyService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper mapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper mapper) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String getXmlInfo() throws IOException {
        return String.join(
                System.lineSeparator(),
                Files.readAllLines(Path.of("src/main/resources/files/xmls/companies.xml")));
    }

    @Override
    public void importEntities() throws IOException, JAXBException {
        String xmlInfo = this.getXmlInfo();
        ByteArrayInputStream infoStream = new ByteArrayInputStream(xmlInfo.getBytes());

        JAXBContext context = JAXBContext.newInstance(CompaniesImportWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        List<Company> companies =
                ((CompaniesImportWrapperDTO) unmarshaller.unmarshal(infoStream))
                        .getCompanies().stream()
                        .map(cDto -> mapper.map(cDto, Company.class))
                        .toList();

        this.companyRepository.saveAll(companies);

    }
}
