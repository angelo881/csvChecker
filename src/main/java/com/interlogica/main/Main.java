package com.interlogica.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaRepositories("com.interlogica.repository")
@ComponentScan({ "com.interlogica.api", "com.interlogica.business" })
@EntityScan("com.interlogica.data")
@EnableWebMvc
public class Main{

	static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {

		new SpringApplication(Main.class).run(args);

		logger.info("main started");

	}

}
