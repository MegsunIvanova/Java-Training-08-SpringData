package bg.softuni.productshop_xml.services;

import bg.softuni.productshop_xml.entities.categories.Category;
import bg.softuni.productshop_xml.entities.categories.CategoriesImportDTO;
import bg.softuni.productshop_xml.entities.products.Product;
import bg.softuni.productshop_xml.entities.products.ProductsImportDTO;
import bg.softuni.productshop_xml.entities.users.User;
import bg.softuni.productshop_xml.entities.users.UsersImportDTO;
import bg.softuni.productshop_xml.repositories.CategoryRepository;
import bg.softuni.productshop_xml.repositories.ProductRepository;
import bg.softuni.productshop_xml.repositories.UserRepository;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class SeedServiceImpl implements SeedService {

    private static final String USERS_XML_PATH = "src/main/resources/productshop/users.xml";
    private static final String CATEGORIES_XML_PATH = "src/main/resources/productshop/categories.xml";
    private static final String PRODUCTS_XML_PATH = "src/main/resources/productshop/products.xml";
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, ModelMapper modelMapper, Gson gson, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public void seedUsers() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(UsersImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        BufferedReader xmlReader = Files.newBufferedReader(Path.of(USERS_XML_PATH));

        UsersImportDTO importDTO = (UsersImportDTO) unmarshaller.unmarshal(xmlReader);

        List<User> entities = importDTO.getUsers().stream()
                .map(dto -> mapper.map(dto, User.class))
                .toList();

        this.userRepository.saveAll(entities);
    }

    @Override
    public void seedCategories() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(CategoriesImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        FileReader xmlReader = new FileReader(CATEGORIES_XML_PATH);
        CategoriesImportDTO importDTO = (CategoriesImportDTO) unmarshaller.unmarshal(xmlReader);

        List<Category> entities = importDTO
                .getCategories()
                .stream()
                .map(cn -> new Category(cn.getName()))
                .toList();

        this.categoryRepository.saveAll(entities);
    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ProductsImportDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        BufferedReader xmlReader = Files.newBufferedReader(Path.of(PRODUCTS_XML_PATH));
        ProductsImportDTO importDTO = (ProductsImportDTO) unmarshaller.unmarshal(xmlReader);

        List<Product> entities = importDTO.getProducts().stream()
                .map(dto -> new Product(dto.getName(), dto.getPrice()))
                .map(this::setRandomCategories)
                .map(this::setRandomSeller)
                .map(this::setRandomBuyer)
                .toList();

        this.productRepository.saveAll(entities);
    }

    private Product setRandomSeller(Product product) {
        Optional<User> seller = this.getRandomUser();

        product.setSeller(seller.get());

        return product;

    }

    private Product setRandomBuyer(Product product) {

        if (product.getPrice().compareTo(BigDecimal.valueOf(944)) > 0) {
            return product;
        }

        Optional<User> buyer = this.getRandomUser();

        product.setBuyer(buyer.get());

        return product;

    }

    private Optional<User> getRandomUser() {
        long usersCount = this.userRepository.count();

        int randomUserId = new Random().nextInt((int) usersCount) + 1;

        return this.userRepository.findById(randomUserId);
    }

    private Product setRandomCategories(Product product) {
        long categoriesDBCount = this.categoryRepository.count();

        int count = new Random().nextInt((int) categoriesDBCount);

        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < count; i++) {
            int id = new Random().nextInt((int) categoriesDBCount) + 1;

            Optional<Category> randomCategory = this.categoryRepository.findById(id);

            categories.add(randomCategory.get());
        }

        product.setCategories(categories);

        return product;

    }

}
