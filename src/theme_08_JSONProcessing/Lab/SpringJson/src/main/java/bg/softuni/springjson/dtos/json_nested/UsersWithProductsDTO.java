package bg.softuni.springjson.dtos.json_nested;

import java.util.List;

public class UsersWithProductsDTO {
    private int usersCount;

    private List<UserWithProductsDTO> users;

    public UsersWithProductsDTO() {
    }

    public UsersWithProductsDTO (List<UserWithProductsDTO> users) {
        this.usersCount = users.size();
        this.users = users;
    }
}
