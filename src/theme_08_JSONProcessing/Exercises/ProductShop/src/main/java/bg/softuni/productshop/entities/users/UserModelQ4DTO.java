package bg.softuni.productshop.entities.users;

import bg.softuni.productshop.entities.products.SoldProductsQ4DTO;

import java.io.Serializable;

public class UserModelQ4DTO implements Serializable {
    private String firstName;
    private String lastName;

    private Integer age;

    private SoldProductsQ4DTO soldProductsDTOS;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SoldProductsQ4DTO getSoldProductsDTOS() {
        return soldProductsDTOS;
    }

    public void setSoldProductsDTOS(SoldProductsQ4DTO soldProductsDTOS) {
        this.soldProductsDTOS = soldProductsDTOS;
    }
}
