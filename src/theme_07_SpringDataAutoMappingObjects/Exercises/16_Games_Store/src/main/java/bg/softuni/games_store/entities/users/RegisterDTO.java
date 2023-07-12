package bg.softuni.games_store.entities.users;


import bg.softuni.games_store.exceptions.ValidationException;

/**
 * Validate the data for registering a user
 * <p>
 * Email must contain @ sign and a period. It must be unique
 * <p>
 * Password - length must be at least 6 symbols and
 * must contain at least 1 uppercase, 1 lowercase letter and 1 digit
 * <p>
 * Confirm Password – must match the provided password.
 */
public class RegisterDTO {

    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    /**
     * commandParts[0] is skipped because it contains the command name
     * which is not needed in the user object
     *
     * @param commandParts all data read from the console
     */
    public RegisterDTO(String[] commandParts) {
        this.email = commandParts[1];
        this.password = commandParts[2];
        this.confirmPassword = commandParts[3];
        this.fullName = commandParts[4];
        this.validate();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    private void validate() {
        int indexOfAt = email.indexOf("@");
        int indexOfDot = email.lastIndexOf(".");
        if (indexOfAt < 0 || indexOfDot < 0 || indexOfAt > indexOfDot) {
            throw new ValidationException("Incorrect email. Email must contain @ and a .!");
        }

        boolean upper = password.chars().anyMatch(Character::isUpperCase);
        boolean lower = password.chars().anyMatch(Character::isLowerCase);
        boolean digit = password.chars().anyMatch(Character::isDigit);

        if (password.length() < 6 || !(upper && lower && digit)) {
            throw new ValidationException("Incorrect password. " +
                    "Password must must be at least 6 symbols " +
                    "and must contain at least 1 uppercase, 1 lowercase letter and 1 digit!");
        }

        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Incorrect password. " +
                    "Password and Confirm Password must match!");
        }
    }
}
