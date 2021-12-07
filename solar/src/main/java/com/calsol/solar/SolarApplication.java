package com.calsol.solar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolarApplication.class, args);
	}

}
