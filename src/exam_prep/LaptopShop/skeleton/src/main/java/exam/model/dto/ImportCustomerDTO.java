package exam.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ImportCustomerDTO {

    @Size(min = 2)
    @NotNull
    private String firstName;

    @Size(min = 2)
    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String registeredOn;

    private ImportCustomerTownDTO town;

    public ImportCustomerDTO() {
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public ImportCustomerTownDTO getTown() {
        return town;
    }

    public void setTown(ImportCustomerTownDTO town) {
        this.town = town;
    }
}
