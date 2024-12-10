package com.aluracursos.ChallengerLiteralura.repository;

import com.aluracursos.ChallengerLiteralura.model.Idiomas;
import com.aluracursos.ChallengerLiteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByIdioma(Idiomas nombreIdioma);
    Optional<Libro> findByTitulo(String titulo);



}
