package bg.softuni.productshop_xml.services;


import bg.softuni.productshop_xml.entities.users.*;
import bg.softuni.productshop_xml.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

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
    public ExportSellersQ2DTO findAllWithSoldProductsAndBuyers() {
        List<User> users = this.userRepository.findAllWithSoldProductsAndBuyers();

        List<UserWithSoldProductsQ2Dto> dTOs =
                users
                        .stream()
                        .map(u -> modelMapper.map(u, UserWithSoldProductsQ2Dto.class))
                        .collect(Collectors.toList());

        return new ExportSellersQ2DTO(dTOs);
    }

    @Override
    @Transactional
    public ExportSellersQ4DTO findAllWithSoldProducts() {
        List<User> users = this.userRepository.findAllWithSoldProducts();

        PropertyMap<User, UserWithSoldProductsQ4Dto> userMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setSoldProducts(source.getSellingItems());
            }
        };

        modelMapper.addMappings(userMap);

        List<UserWithSoldProductsQ4Dto> dTOs = users.stream()
                .map(user -> modelMapper.map(user, UserWithSoldProductsQ4Dto.class))
                .toList();

        return new ExportSellersQ4DTO(dTOs);
    }
}
