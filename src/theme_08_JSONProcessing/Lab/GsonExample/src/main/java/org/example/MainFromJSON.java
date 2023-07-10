package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dtos.CourseDTO;
import org.example.dtos.StudentAdditionalInfoDTO;
import org.example.dtos.StudentDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MainFromJSON {
    public static void main(String[] args) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setDateFormat("dd-MM-yyyy")
                .setPrettyPrinting()
                .create();

        StudentDTO studentDto = new StudentDTO(
                "Pesho",
                null, 22,
                new StudentAdditionalInfoDTO(false, 5.38),
                List.of(new CourseDTO("Math", 20),
                        new CourseDTO("Biology", 15)));

        String json = "{ \"isGraduated\": false, \"averageGrade\": 5.38 }";

        String notFormatted = """
                { 
                "isGraduated": false, 
                "averageGrade": 5.38 }
                """;

        String studentJson = """
                {
                  "firstName": "Pesho",
                  "lastName": null,
                  "age": 22,
                  "additionalInfoDTO": {
                    "isGraduated": false,
                    "averageGrade": 5.38
                  },
                  "courses": [
                    {
                      "name": "Math",
                      "lengthInWeeks": 20
                    },
                    {
                      "name": "Biology",
                      "lengthInWeeks": 15
                    }
                  ]
                }
                """;

        String jsonArray = """
                [
                  {
                    "firstName": "Pesho",
                    "lastName": null,
                    "age": 22,
                    "additionalInfoDTO": {
                      "isGraduated": false,
                      "averageGrade": 5.38
                    },
                    "courses": [
                      {
                        "name": "Math",
                        "lengthInWeeks": 20
                      },
                      {
                        "name": "Biology",
                        "lengthInWeeks": 15
                      }
                    ]
                  }
                ]                                
                """;

        StudentAdditionalInfoDTO studentAdditionalInfoDTO = gson.fromJson(notFormatted, StudentAdditionalInfoDTO.class);
        StudentDTO studentDTO = gson.fromJson(studentJson, StudentDTO.class);
        StudentDTO[] studentDTOArr = gson.fromJson(jsonArray, StudentDTO[].class);


        System.out.println(studentAdditionalInfoDTO);
        System.out.println(studentDTO);
        System.out.println(Arrays.toString(studentDTOArr));

    }
}
