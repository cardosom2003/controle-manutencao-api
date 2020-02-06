package com.cardoso.controlemanutencao.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class ControleManutencaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleManutencaoApplication.class, args);
	}

}
