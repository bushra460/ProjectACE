package ca.gc.cbsa.mcoe.deltaace.restApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
@SpringBootApplication()
public class DeltaAceRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeltaAceRestApiApplication.class, args);
	}

}

