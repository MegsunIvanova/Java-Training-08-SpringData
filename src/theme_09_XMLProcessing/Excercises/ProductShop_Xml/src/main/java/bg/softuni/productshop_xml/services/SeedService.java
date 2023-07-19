package bg.softuni.productshop_xml.services;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedUsers() throws IOException, JAXBException;
    void seedCategories() throws FileNotFoundException, JAXBException;
    void seedProducts() throws IOException, JAXBException;

    default void seedAll() throws IOException, JAXBException {
        this.seedCategories();
        this.seedUsers();
        this.seedProducts();
    }

}
