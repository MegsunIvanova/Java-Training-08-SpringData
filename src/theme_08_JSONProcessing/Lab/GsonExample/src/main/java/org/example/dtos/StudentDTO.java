package org.example.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class StudentDTO implements Serializable {
    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private int age;

    @Expose
    private StudentAdditionalInfoDTO additionalInfoDTO;


    @Expose
    private List<CourseDTO> courses;

    public StudentDTO(String firstName, String lastName, int age,
                      StudentAdditionalInfoDTO additionalInfoDTO, List<CourseDTO> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.additionalInfoDTO = additionalInfoDTO;
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "StudentDTO {" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", additionalInfoDTO=" + additionalInfoDTO +
                ", courses=" + courses +
                '}';
    }
}
