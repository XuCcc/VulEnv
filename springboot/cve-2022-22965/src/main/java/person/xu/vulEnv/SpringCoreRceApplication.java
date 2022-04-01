package person.xu.vulEnv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringCoreRceApplication {
    @GetMapping("/")
    public String name(User user) {
        return user.getName();
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringCoreRceApplication.class, args);
    }

}
