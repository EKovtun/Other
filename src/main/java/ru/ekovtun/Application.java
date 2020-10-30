package ru.ekovtun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.ekovtun.entities.Plane;
import ru.ekovtun.repositories.PlaneRepository;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        PlaneRepository planeRepository = context.getBean(PlaneRepository.class);
        planeRepository.save(new Plane("aix123"));
        planeRepository.save(new Plane("bas115"));
        System.out.println(planeRepository.findAll());
    }
}
