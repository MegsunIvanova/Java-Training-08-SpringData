package softuni.exam.instagraphlite.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String caption;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Picture picture;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Post() {
    }

    public Post(String caption, Picture picture, User user) {
        this.caption = caption;
        this.picture = picture;
        this.user = user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
