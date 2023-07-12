package bg.softuni.games_store.exceptions;

public class UserNotAdminException extends RuntimeException {
    public UserNotAdminException() {
        super("Only administrators can add, edit or delete games!");
    }
}
