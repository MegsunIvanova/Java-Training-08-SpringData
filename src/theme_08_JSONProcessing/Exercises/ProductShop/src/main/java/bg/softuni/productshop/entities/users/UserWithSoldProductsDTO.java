package bg.softuni.productshop.entities.users;

import bg.softuni.productshop.entities.products.SoldProductsDTO;

import java.util.List;

public class UserWithSoldProductsDTO {
    private String firstName;
    private String lastName;

    private List<SoldProductsDTO> soldProducts;

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

    public List<SoldProductsDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductsDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
