package com.aluracursos.ChallengerLiteralura.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;


    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    // Constructor vacío necesario para JPA
    public Autor() {
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        String listaLibros = (libros != null && !libros.isEmpty())
                ? libros.stream()
                .map(Libro::getTitulo)
                .reduce((titulo1, titulo2) -> titulo1 + ", " + titulo2)
                .orElse("")
                : "N/A";

        return "Autor: " + nombre + "\n" +
                "Fecha de nacimiento: " + (fechaDeNacimiento != null ? fechaDeNacimiento : "N/A") + "\n" +
                "Fecha de fallecimiento: " + (fechaDeFallecimiento != null ? fechaDeFallecimiento : "N/A") + "\n" +
                "Libros: " + listaLibros + "\n";
    }

    //Constructor que recibe un objeto DatosAutor
    public Autor (DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = (datosAutor.fechaNacimiento());
        this.fechaDeFallecimiento = datosAutor.fechaFallecimiento();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }
}
