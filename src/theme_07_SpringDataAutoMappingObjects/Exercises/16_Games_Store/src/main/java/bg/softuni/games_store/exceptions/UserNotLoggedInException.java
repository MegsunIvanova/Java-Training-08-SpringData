package bg.softuni.games_store.exceptions;

public class UserNotLoggedInException extends RuntimeException{

    public UserNotLoggedInException() {
        super("No user was logged in.");
    }
}
