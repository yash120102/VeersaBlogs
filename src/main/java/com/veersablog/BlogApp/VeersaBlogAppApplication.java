package com.veersablog.BlogApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VeersaBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeersaBlogAppApplication.class, args);
	}

	@Bean
   public ModelMapper modelMapper() {
		return new ModelMapper();
   }
}
