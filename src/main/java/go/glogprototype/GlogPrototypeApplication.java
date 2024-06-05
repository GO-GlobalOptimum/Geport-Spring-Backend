package go.glogprototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "go.glogprototype.domain")
public class GlogPrototypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlogPrototypeApplication.class, args);
    }

}
