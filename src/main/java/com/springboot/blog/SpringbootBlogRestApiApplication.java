package com.springboot.blog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "SpringBoot Blog App REST APIs",
				description = "SpringBoot Blog App REST APIs documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Shakti Mishra",
						email = "skmech99@gmail.com",
						url = "https://www.linkedin.com/in/shakti-mishra-mech99/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "abc.com"
				)
		)
)
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
