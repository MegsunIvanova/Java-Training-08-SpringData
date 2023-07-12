package bg.softuni.games_store.exceptions;

public class NoSuchGameException extends RuntimeException {
    public NoSuchGameException (){
        super("No Such game");
    }
}
