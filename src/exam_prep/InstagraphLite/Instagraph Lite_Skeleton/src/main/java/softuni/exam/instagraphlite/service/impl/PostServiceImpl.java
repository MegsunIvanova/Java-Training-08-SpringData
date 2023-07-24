package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PostImportDTO;
import softuni.exam.instagraphlite.models.dto.PostImportWrapperDTO;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtils;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static softuni.exam.instagraphlite.models.Constants.*;

@Service
public class PostServiceImpl implements PostService {

    private final String POSTS_XML_FILE = "src/main/resources/files/posts.xml";
    private final PostRepository postRepository;
    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, PictureRepository pictureRepository,
                           UserRepository userRepository, XmlParser xmlParser,
                           ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_XML_FILE));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        List<PostImportDTO> postDTOs =
                this.xmlParser.fromFile(POSTS_XML_FILE, PostImportWrapperDTO.class).getPosts();

        List<String> resultMessages = new ArrayList<>();

        for (PostImportDTO dto : postDTOs) {

            Optional<Picture> pictureByPath = this.pictureRepository.findFirstByPath(dto.getPicture().getPath());
            Optional<User> userByUsername = this.userRepository.findFirstByUsername(dto.getUser().getUsername());

            if (pictureByPath.isEmpty() || userByUsername.isEmpty() || validationUtils.isInvalid(dto)) {
                resultMessages.add(String.format(INVALID_FORMAT, POST));
                continue;
            }

            Post entity = new Post(dto.getCaption(), pictureByPath.get(), userByUsername.get());

            this.postRepository.save(entity);

            resultMessages.add(String.format(SUCCESSFUL_FORMAT_POST,
                    entity.getUser().getUsername()));
        }

        return String.join(System.lineSeparator(), resultMessages);
    }
}
