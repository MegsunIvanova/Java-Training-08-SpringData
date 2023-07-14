package bg.softuni.productshop.services;

import bg.softuni.productshop.entities.users.UserWithSoldProductsDTO;
import bg.softuni.productshop.entities.users.UsersSoldProductsQ4DTO;

import java.util.List;

public interface UserService {

    List<UserWithSoldProductsDTO> getUsersWithSoldProducts();

    UsersSoldProductsQ4DTO getUsersWithSoldProductsOrderByCount();
}
