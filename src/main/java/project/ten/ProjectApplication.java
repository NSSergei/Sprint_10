package project.ten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@SpringBootApplication

public class ProjectApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
}
