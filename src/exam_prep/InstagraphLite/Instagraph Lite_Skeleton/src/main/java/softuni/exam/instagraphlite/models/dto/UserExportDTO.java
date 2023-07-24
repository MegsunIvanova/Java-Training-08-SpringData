package softuni.exam.instagraphlite.models.dto;

import softuni.exam.instagraphlite.models.entity.Post;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserExportDTO {

    private String username;

    private List<UserPostExportDTO> posts;

    public UserExportDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserPostExportDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<UserPostExportDTO> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        if (posts.size() == 0) {
            return String.format("User: %s%n" +
                            "Post count: %d",
                    this.username,
                    this.posts.size());
        }

        String postsToString = this.posts
                .stream()
                .sorted(Comparator.comparing(p -> p.getPicture().getSize()))
                .map(UserPostExportDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));

        return String.format("User: %s%n" +
                        "Post count: %d%n" +
                        "%s",
                this.username,
                this.posts.size(),
                postsToString);
    }
}
