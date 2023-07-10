package bg.softuni.springjson.dtos;

import java.util.List;

public class StudentDTO {
    private String firstName;
    private int age;
    private boolean isGraduated;
    private List<String> courses;

    public StudentDTO(String firstName, int age, boolean isGraduated, List<String> courses) {
        this.firstName = firstName;
        this.age = age;
        this.isGraduated = isGraduated;
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "StudentDTO {" +
                "firstName='" + firstName + '\'' +
                ", age=" + age +
                ", isGraduated=" + isGraduated +
                ", courses=" + courses +
                '}';
    }
}
