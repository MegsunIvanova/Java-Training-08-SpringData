package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureImportDTO;
import softuni.exam.instagraphlite.models.dto.UserExportDTO;
import softuni.exam.instagraphlite.models.dto.UserImportDTO;
import softuni.exam.instagraphlite.models.dto.UserPictureExportDTO;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.models.Constants.*;

@Service
public class UserServiceImpl implements UserService {

    private final String USERS_JSON_FILE = "src/main/resources/files/users.json";

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository,
                           Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(USERS_JSON_FILE));
    }

    @Override
    public String importUsers() throws IOException {
        UserImportDTO[] userDTOs =
                gson.fromJson(this.readFromFileContent(), UserImportDTO[].class);

        List<String> resultMessages = new ArrayList<>();

        for (UserImportDTO dto : userDTOs) {

            Optional<User> userByUsername = this.userRepository.findFirstByUsername(dto.getUsername());
            Optional<Picture> pictureByPath = this.pictureRepository.findFirstByPath(dto.getProfilePicture());

            if (userByUsername.isPresent() || pictureByPath.isEmpty() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, USER));
                continue;
            }

            User entity = modelMapper.map(dto, User.class);
            entity.setProfilePicture(pictureByPath.get());

            this.userRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT_USER,
                    entity.getUsername()));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }

    @Override
    @Transactional
    public String exportUsersWithTheirPosts() {
        List<User> users = this.userRepository.findAllUsersWithPosts();

        return users.stream()
                .map(user -> modelMapper.map(user, UserExportDTO.class))
                .map(UserExportDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

    }
}
