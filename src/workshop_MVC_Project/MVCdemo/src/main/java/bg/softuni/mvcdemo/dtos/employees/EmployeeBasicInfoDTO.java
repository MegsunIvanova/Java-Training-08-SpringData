package bg.softuni.mvcdemo.dtos.employees;

import bg.softuni.mvcdemo.entities.Project;

public class EmployeeBasicInfoDTO {

    private String firstName;

    private String lastName;

    private Integer age;

    private String projectName;

    public EmployeeBasicInfoDTO() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s%n" +
                        "   Age: %d%n" +
                        "   Project name: %s",
                this.firstName,
                this.lastName,
                this.age,
                this.projectName);
    }
}
