package com.noble.developers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class CSVImporterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CSVImporterApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(){
		return args -> {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");

			System.out.println(dateFormatter.parse("1/1/2019"));
		};
	}

}
