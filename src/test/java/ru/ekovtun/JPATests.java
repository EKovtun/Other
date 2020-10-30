package ru.ekovtun;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.ekovtun.entities.Plane;
import ru.ekovtun.repositories.PlaneRepository;

@EnableJpaRepositories(value = "ru.ekovtun.repositories")
@ContextConfiguration(classes = Application.class, loader = AnnotationConfigContextLoader.class)
public class JPATests{

    @Autowired
    private PlaneRepository planeRepository;

    @Test
    void contextLoads() {
        planeRepository.save(new Plane("aix123"));
    }
}


