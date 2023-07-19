package bg.softuni.productshop_xml;

import bg.softuni.productshop_xml.entities.categories.ExportCategoriesWithProductsDTO;
import bg.softuni.productshop_xml.entities.products.ExportProductsInRangeDTO;
import bg.softuni.productshop_xml.entities.users.ExportSellersQ2DTO;
import bg.softuni.productshop_xml.entities.users.ExportSellersQ4DTO;
import bg.softuni.productshop_xml.services.CategoryService;
import bg.softuni.productshop_xml.services.ProductService;
import bg.softuni.productshop_xml.services.SeedService;
import bg.softuni.productshop_xml.services.UserService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;

    private final CategoryService categoryService;


    @Autowired
    public ProductShopRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;

        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAll();

//        this.q01_productsInRange();
//        this.q02_findUsersWithSoldProducts();
//        this.q03_findCategoriesWithNumberOfProducts();
        this.q04_findUsersWithSoldProducts();
    }

    private void q04_findUsersWithSoldProducts() throws JAXBException {
        ExportSellersQ4DTO dTOs =
                this.userService.findAllWithSoldProducts();

        JAXBContext context = JAXBContext.newInstance(ExportSellersQ4DTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dTOs, System.out);
    }

    private void q03_findCategoriesWithNumberOfProducts() throws JAXBException {
        ExportCategoriesWithProductsDTO dTOs =
                this.categoryService.findCategoriesWithNumberOfProducts();

        JAXBContext context = JAXBContext.newInstance(ExportCategoriesWithProductsDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dTOs, System.out);
    }

    private void q02_findUsersWithSoldProducts() throws JAXBException {
        ExportSellersQ2DTO dTOs = this.userService.findAllWithSoldProductsAndBuyers();

        JAXBContext context = JAXBContext.newInstance(ExportSellersQ2DTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dTOs, System.out);

    }

    private void q01_productsInRange() throws JAXBException {
        ExportProductsInRangeDTO dTOs = this.productService.getInRange(500.0, 1000.0);

        JAXBContext context = JAXBContext.newInstance(ExportProductsInRangeDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(dTOs, System.out);

    }


}
