package com.api.nach;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;



@Configuration


public class OpenApiConfig {
	 
		
	    @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .components(new Components())
	                .info(new Info().title("NACH API SERVICES").description(
	                        "This is a NACH Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
	    }
	}


