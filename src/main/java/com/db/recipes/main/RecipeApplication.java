/**
 * 
 */
package com.db.recipes.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

/**
 * @author Geetha
 * 
 * This is Entry point to start SpringBoot application.
 * SpringBoot Application internally called Controllers and remaining dependencies
 * This application is embed with Tomcat Server.
 *
 */
@SpringBootApplication
@Configuration
@ComponentScan("com.db.recipes")
@Log4j2
public class RecipeApplication {

	/**
	 * This is SpringBoot main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		log.info("Starting SpringBoot Application Recipes");
		SpringApplication.run(RecipeApplication.class, args);
	}
	/**
	 * @param application
	 * @return SpringApplicationBuilder
	 */
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RecipeApplication.class);
	}

}
