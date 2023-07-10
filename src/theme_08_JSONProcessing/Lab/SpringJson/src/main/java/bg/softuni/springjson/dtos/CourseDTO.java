package bg.softuni.springjson.dtos;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;

public class CourseDTO {

    @Expose
    private String name;

    @Expose
    private int lengthInWeeks;

    public CourseDTO(String name, int lengthInWeeks) {
        this.name = name;
        this.lengthInWeeks = lengthInWeeks;
    }

    @Override
    public String toString() {
        return "CourseDTO {" +
                "name='" + name + '\'' +
                ", lengthInWeeks=" + lengthInWeeks +
                '}';
    }
}
