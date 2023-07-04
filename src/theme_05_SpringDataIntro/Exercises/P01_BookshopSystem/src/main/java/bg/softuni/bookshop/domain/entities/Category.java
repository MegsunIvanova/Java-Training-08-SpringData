package bg.softuni.bookshop.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false)
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
