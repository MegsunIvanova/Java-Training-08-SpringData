package bg.softuni.games_store.entities.users;

public class LoginDTO {

    private String email;
    private String password;

    /**
     * commandParts[0] is skipped because it contains the command name
     * which is not needed in the user object
     *
     * @param commandParts all data read from the console
     */
    public LoginDTO(String[] commandParts) {
        this.email = commandParts[1];
        this.password = commandParts[2];
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
