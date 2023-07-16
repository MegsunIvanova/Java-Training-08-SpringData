package bg.softuni.cardealer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class Config {

    @Bean
    ModelMapper createModelMapper() {
        return new ModelMapper();
    }

    @Bean
    Gson createGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
    }


}
