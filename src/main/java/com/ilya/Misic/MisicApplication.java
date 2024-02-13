package com.ilya.Misic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableSpringDataWebSupport
@EnableTransactionManagement
public class MisicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MisicApplication.class, args);
	}

}
