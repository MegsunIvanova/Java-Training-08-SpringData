package softuni.exam.models.dto;

import org.springframework.beans.factory.annotation.Value;

public class ExportPassengerView {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Integer numberOfTickets;

    public ExportPassengerView(String firstName, String lastName,
                               String email, String phoneNumber,
                               Integer numberOfTickets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.numberOfTickets = numberOfTickets;
    }

    @Override
    public String toString() {
        return String.format("Passenger %s  %s%n" +
                "\tEmail - %s%n" +
                "\tPhone - %s%n" +
                "\tNumber of tickets - %d",
                this.firstName,
                this.lastName,
                this.email,
                this.phoneNumber,
                this.numberOfTickets);
    }
}
