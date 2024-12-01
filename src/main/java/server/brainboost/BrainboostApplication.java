package server.brainboost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BrainboostApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrainboostApplication.class, args);
	}

}
