package com.cledson.springfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringfileApplication {
	private static boolean serverStarted = false;

	/**
	 * Inicia o servidor SpringBoot
	 * @param args os argumentos
	 */
	public static void startServer(String[] args) {
		if (serverStarted) {
			return;
		}

		SpringApplication.run(SpringfileApplication.class, args);
		serverStarted = true;
	}
}