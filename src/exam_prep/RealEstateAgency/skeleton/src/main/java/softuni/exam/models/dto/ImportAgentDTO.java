package softuni.exam.models.dto;

import org.springframework.lang.NonNull;
import softuni.exam.models.entity.Town;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class ImportAgentDTO {

    @NonNull
    @Size(min = 2)
    private String firstName;

    @NonNull
    @Size(min = 2)
    private String lastName;

    @NonNull
    private String town;

    @NonNull
    @Email
    private String email;

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getTown() {
        return town;
    }

    public void setTown(@NonNull String town) {
        this.town = town;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }
}
