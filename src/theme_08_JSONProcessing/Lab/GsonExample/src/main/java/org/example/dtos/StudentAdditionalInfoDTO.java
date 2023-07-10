package org.example.dtos;

import com.google.gson.annotations.Expose;

public class StudentAdditionalInfoDTO {

    @Expose
    private boolean isGraduated;

    @Expose
    private double averageGrade;


    public StudentAdditionalInfoDTO(boolean isGraduated, double averageGrade) {
        this.isGraduated = isGraduated;
        this.averageGrade = averageGrade;
    }

    @Override
    public String toString() {
        return "StudentAdditionalInfoDTO {" +
                "isGraduated=" + isGraduated +
                ", averageGrade=" + averageGrade +
                '}';
    }
}
