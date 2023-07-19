package bg.softuni.productshop_xml.entities.users;

import bg.softuni.productshop_xml.entities.products.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private Integer age;

    @OneToMany(targetEntity = Product.class, mappedBy = "seller")
    private List<Product> sellingItems;

    @OneToMany(targetEntity = Product.class, mappedBy = "buyer")
    private List<Product> boughtProducts;

    @ManyToMany
    private Set<User> friends;

    public User() {
        this.sellingItems = new ArrayList<>();
        this.boughtProducts = new ArrayList<>();
        this.friends = new HashSet<>();
    }

    public User(String firstName, String lastName, Integer age) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Product> getSellingItems() {
        return sellingItems;
    }

    public void setSellingItems(List<Product> sellingItems) {
        this.sellingItems = sellingItems;
    }

    public List<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(List<Product> itemsBought) {
        this.boughtProducts = itemsBought;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public String getFullName() {
        return this.firstName == null ? this.lastName : this.firstName + " " + this.lastName;
    }
}
