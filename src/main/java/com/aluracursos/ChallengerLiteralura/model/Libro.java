package com.aluracursos.ChallengerLiteralura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Libros")

public class Libro {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   //@Column(unique = true)
   private String titulo;


   @Enumerated(EnumType.STRING)
   private Idiomas idioma;

   private Double numeroDeDescargas;

  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
  @JoinColumn(name = "autor_id")
  private Autor autor;

   // Constructor vacío necesario para JPA
   public Libro(){
   }


   @Override
   public String toString() {
      return  " Título: " + titulo  + "\n" +
              " Autor: " + autor.getNombre() + "\n" +
              " Idioma: " + idioma.toCamelCase() + "\n" +
              " Número de descargas:" + numeroDeDescargas + "\n"
              + "\n"
              + "-------------------------------"
              + "\n";
   }

   public Libro(DatosLibros datosLibros){
   this.titulo = datosLibros.titulo();

  if (datosLibros.idiomas() != null && !datosLibros.idiomas().isEmpty()) {
     //Tomar el primer idioma de la lista
     String primerIdioma = datosLibros.idiomas().get(0);
     this.idioma = Idiomas.fromString(primerIdioma);

  } else {
     this.idioma = Idiomas.ESPAÑOL;
  }
   this.numeroDeDescargas = datosLibros.numeroDeDescargas();

}

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitulo() {
      return titulo;
   }

   public void setTitulo(String titulo) {
      this.titulo = titulo;
   }

   public Idiomas getIdioma() {
      return idioma;
   }

   public void setIdioma(Idiomas idioma) {
      this.idioma = idioma;
   }

   public Double getNumeroDeDescargas() {
      return numeroDeDescargas;
   }

   public void setNumeroDeDescargas(Double numeroDeDescargas) {
      this.numeroDeDescargas = numeroDeDescargas;
   }

   public Autor getAutor() {
      return autor;
   }

   public void setAutor(Autor autor) {
      this.autor = autor;
   }

//   public List<Autor> getAutores() {
//      return autores;
//   }
//
//   public void setAutores(List<Autor> autores) {
//      this.autores = autores;
//   }
}





