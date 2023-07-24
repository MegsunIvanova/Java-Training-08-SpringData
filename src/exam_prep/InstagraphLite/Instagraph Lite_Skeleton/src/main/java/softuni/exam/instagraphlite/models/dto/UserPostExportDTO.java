package softuni.exam.instagraphlite.models.dto;

import softuni.exam.instagraphlite.models.entity.Picture;

public class UserPostExportDTO {

    private String caption;

    private UserPictureExportDTO picture;

    public UserPostExportDTO() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserPictureExportDTO getPicture() {
        return picture;
    }

    public void setPicture(UserPictureExportDTO picture) {
        this.picture = picture;
    }


    @Override
    public String toString() {

        return String.format("==Post Details:%n" +
                "----Caption: %s%n" +
                "----Picture Size: %.2f",
                this.caption,
                this.picture.getSize());
    }
}
