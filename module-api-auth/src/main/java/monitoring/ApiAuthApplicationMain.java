package monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ApiAuthApplicationMain {

	public static void main(String[] args) {
		SpringApplication.run(ApiAuthApplicationMain.class, args);
	}

}
