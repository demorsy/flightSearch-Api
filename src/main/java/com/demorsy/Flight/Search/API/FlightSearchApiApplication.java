package com.demorsy.Flight.Search.API;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlightSearchApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightSearchApiApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${application-desc}") String description,
								 @Value("${app-version") String version){
		return new OpenAPI()
				.info(new Info()
						.title("Flight API")
						.version(version)
						.description(description)
						);
	}

}
