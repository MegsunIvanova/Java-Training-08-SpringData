package bg.softuni.springjson.dtos.json_nested;

public class UserWithProductsDTO {
    private String firstName;
    private String lastName;
    private int age;
    private SoldProductsDTO soldProducts;

    public UserWithProductsDTO() {
    }

    public UserWithProductsDTO(String firstName, String lastName, int age, SoldProductsDTO soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
    }
}

