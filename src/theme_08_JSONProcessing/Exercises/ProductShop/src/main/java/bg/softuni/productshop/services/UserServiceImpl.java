package bg.softuni.productshop.services;


import bg.softuni.productshop.entities.users.UserWithSoldProductsDTO;
import bg.softuni.productshop.entities.products.ProductWithNameAndPriceDTO;
import bg.softuni.productshop.entities.products.SoldProductsQ4DTO;
import bg.softuni.productshop.entities.users.User;
import bg.softuni.productshop.entities.users.UserModelQ4DTO;
import bg.softuni.productshop.entities.users.UsersSoldProductsQ4DTO;
import bg.softuni.productshop.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<UserWithSoldProductsDTO> getUsersWithSoldProducts() {

        List<User> usersWithSoldProducts = this.userRepository.findAllWithSoldProducts();

        return usersWithSoldProducts.stream()
                .map(user -> this.modelMapper.map(user, UserWithSoldProductsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsersSoldProductsQ4DTO getUsersWithSoldProductsOrderByCount() {
        List<User> users = this.userRepository.findAllBySoldProductsNotEmptyEmptyOrderBySoldProductsDescLastNameAsc();

        List<UserModelQ4DTO> userModelQ4DTOs = users.stream().map(u -> {
            UserModelQ4DTO userDTO = this.modelMapper.map(u, UserModelQ4DTO.class);

            List<ProductWithNameAndPriceDTO> soldProducts = u.getSellingItems().stream().filter(p -> p.getBuyer() != null)
                    .map(p -> this.modelMapper.map(p, ProductWithNameAndPriceDTO.class))
                    .collect(Collectors.toList());

            userDTO.setSoldProductsDTOS(new SoldProductsQ4DTO(soldProducts));

            return userDTO;

        }).collect(Collectors.toList());

        return new UsersSoldProductsQ4DTO(userModelQ4DTOs);

    }
}
