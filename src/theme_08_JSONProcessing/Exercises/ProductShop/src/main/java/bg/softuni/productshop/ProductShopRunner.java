package bg.softuni.productshop;

import bg.softuni.productshop.entities.categories.CategoryStatsDTO;
import bg.softuni.productshop.entities.products.ProductWithoutBuyerDTO;
import bg.softuni.productshop.entities.users.UserWithSoldProductsDTO;
import bg.softuni.productshop.entities.users.UsersSoldProductsQ4DTO;
import bg.softuni.productshop.services.ProductService;
import bg.softuni.productshop.services.SeedService;
import bg.softuni.productshop.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductShopRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;

    private final Gson gson;

    @Autowired
    public ProductShopRunner(SeedService seedService, ProductService productService, UserService userService, Gson gson) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedService.seedAll();
//        this.query_01_ProductsInRange(500, 100);
//        this.query_02_SuccessfullySoldProducts();
//        this.query_03_CategoriesByProductsCount();
        this.query_04_UsersAndProducts();
    }


    private void query_01_ProductsInRange(float rangeStart, float rangeEnd) {
        List<ProductWithoutBuyerDTO> productsForSell =
                this.productService.getProductsInPriceRangeForSell(rangeStart, rangeEnd);

        String json = this.gson.toJson(productsForSell);

        System.out.println(json);
    }

    private void query_02_SuccessfullySoldProducts() {
        List<UserWithSoldProductsDTO> usersWithSoldProducts =
                this.userService.getUsersWithSoldProducts();

        String json = this.gson.toJson(usersWithSoldProducts);

        System.out.println(json);
    }

    private void query_03_CategoriesByProductsCount() {
        List<CategoryStatsDTO> categoryStatistics =
                this.productService.getCategoryStatistics();

        String json = this.gson.toJson(categoryStatistics);

        System.out.println(json);
    }

    private void query_04_UsersAndProducts() {

        UsersSoldProductsQ4DTO usersWithSoldProductsOrderByCount = this.userService.getUsersWithSoldProductsOrderByCount();
        String json = this.gson.toJson(usersWithSoldProductsOrderByCount);

        System.out.println(json);
    }

}
