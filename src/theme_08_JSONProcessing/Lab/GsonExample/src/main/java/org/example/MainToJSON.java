package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dtos.CourseDTO;
import org.example.dtos.StudentAdditionalInfoDTO;
import org.example.dtos.StudentDTO;

import java.util.List;

public class MainToJSON {
    public static void main(String[] args) {

        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        StudentDTO studentDto = new StudentDTO(
                "Pesho",
                null, 22,
                new StudentAdditionalInfoDTO(false, 5.38),
                List.of(new CourseDTO("Math", 20),
                        new CourseDTO("Biology", 15)));

        String json = gson.toJson(studentDto);
        System.out.println(json);

        List<StudentDTO> studentDTOList = List.of(studentDto);
        String jsonList = gson.toJson(studentDTOList);
        System.out.println(jsonList);


    }
}