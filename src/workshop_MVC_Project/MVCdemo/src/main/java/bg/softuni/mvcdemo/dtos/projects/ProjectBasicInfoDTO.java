package bg.softuni.mvcdemo.dtos.projects;

import java.math.BigDecimal;

public class ProjectBasicInfoDTO {

    private String name;

    private String description;

    private BigDecimal payment;

    public ProjectBasicInfoDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return String.format("Project Name: %s%n" +
                        "   Description: %s%n" +
                        "   %.2f",
                this.name,
                this.description,
                this.payment);
    }
}
