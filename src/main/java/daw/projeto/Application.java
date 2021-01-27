package daw.projeto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		logger.info(">>>> Iniciando a execucao da aplicacao");
		SpringApplication.run(Application.class, args);
		logger.info(">>>> Aplicacao inicializada");
	}

}