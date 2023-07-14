package bg.softuni.productshop.entities.users;

import java.io.Serializable;
import java.util.List;

public class UsersSoldProductsQ4DTO implements Serializable {
    private int usersCount;

    private List<UserModelQ4DTO> users;

    public UsersSoldProductsQ4DTO(List<UserModelQ4DTO> users) {
        this.usersCount = users.size();
        this.users = users;
    }
}
