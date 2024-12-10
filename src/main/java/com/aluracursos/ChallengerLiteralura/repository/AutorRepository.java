package com.aluracursos.ChallengerLiteralura.repository;

import com.aluracursos.ChallengerLiteralura.model.Autor;
import com.aluracursos.ChallengerLiteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    // Código trae información del autor
    Optional<Autor> findByNombre(String nombre);

    //Query que trae información de autores vivos desde base de datos, según año de consulta
    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :fechaConsulta AND (a.fechaDeFallecimiento > :fechaConsulta OR a.fechaDeFallecimiento IS NULL)")
    List<Autor> findAutoresByFecha(@Param("fechaConsulta") Integer fechaConsulta);

    @Query("SELECT a FROM Autor a JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();
}
