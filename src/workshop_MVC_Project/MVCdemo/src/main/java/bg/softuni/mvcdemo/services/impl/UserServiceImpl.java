package bg.softuni.mvcdemo.services.impl;

import bg.softuni.mvcdemo.dtos.users.UserRegisterDTO;
import bg.softuni.mvcdemo.entities.User;
import bg.softuni.mvcdemo.repositories.UserRepository;
import bg.softuni.mvcdemo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(UserRegisterDTO registerDTO) {

        boolean emailInUse = userRepository.existsByEmail(registerDTO.getEmail());

        if (emailInUse) {
            return false;
        }

        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(registerDTO, User.class);
        userRepository.save(user);

        return true;
    }
}
