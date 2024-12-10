package com.aluracursos.ChallengerLiteralura;

import com.aluracursos.ChallengerLiteralura.model.Libro;
import com.aluracursos.ChallengerLiteralura.principal.Principal;
import com.aluracursos.ChallengerLiteralura.repository.AutorRepository;
import com.aluracursos.ChallengerLiteralura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengerLiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ChallengerLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraElMenu();

	}
}
