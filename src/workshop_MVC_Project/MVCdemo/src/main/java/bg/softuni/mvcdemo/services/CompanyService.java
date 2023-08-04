package bg.softuni.mvcdemo.services;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface CompanyService {

    boolean areImported();

    String getXmlInfo () throws IOException;

    void importEntities() throws IOException, JAXBException;
}
