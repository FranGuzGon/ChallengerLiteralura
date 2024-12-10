package com.aluracursos.ChallengerLiteralura.principal;

import com.aluracursos.ChallengerLiteralura.ChallengerLiteraluraApplication;
import com.aluracursos.ChallengerLiteralura.excepcion.LibroNoEncontradoExcepcion;
import com.aluracursos.ChallengerLiteralura.excepcion.LibroYaExisteException;
import com.aluracursos.ChallengerLiteralura.model.*;
import com.aluracursos.ChallengerLiteralura.repository.AutorRepository;
import com.aluracursos.ChallengerLiteralura.repository.LibroRepository;
import com.aluracursos.ChallengerLiteralura.service.ConsumoAPI;
import com.aluracursos.ChallengerLiteralura.service.ConvierteDatos;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;


public class Principal {

    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<Libro> datosLibro = new ArrayList<>();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            System.out.println("-------------------------------");
            System.out.println("\n" + "Elija la opción a través de su número" + "\n");
            System.out.println();
            System.out.println("1- buscar libro por título");
            System.out.println("2- listar libros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos en un determinado año");
            System.out.println("5- listar libros por idioma");
            System.out.println("0- salir ");
            System.out.println();

            try {
                opcion = lectura.nextInt();
                lectura.nextLine();

                if (opcion < 0 || opcion > 5) { // Validación de rango
                    System.out.println("Opción no válida. Por favor, ingrese un número entre 0 y 5.");
                    continue; // Regresa al inicio del ciclo
                }

                switch (opcion) {

                    case 1:
                        buscarLibrosPorTitulo();
                        break;

                    case 2:
                        listarLibrosRegistrados();
                        break;

                    case 3:
                        listarAutoresRegistrados();
                        break;

                    case 4:
                        listarAutoresVivosEnFechaDeterminada();
                        break;

                    case 5:
                        listarLibrosPorIdioma();
                        break;

                    case 0:
                        System.out.println("Gracias por usar el programa. ¡Hasta pronto!");
                        break;

                    default:
                        System.out.println("Opcion no válida. Intente nuevamente");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                lectura.nextLine();
            }
        }
    }

    //Información libro buscado
    private DatosLibros getDatosLibros() {
        System.out.println("Escriba el nombre del libro que desea buscar");
        var tituloLibro = lectura.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        //System.out.println("Respuesta de la API" + json);

        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        var libroEncontrado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        //Si no se encuentra el libro, lanzamos una excepción
        if (libroEncontrado.isEmpty()) {
            throw new LibroNoEncontradoExcepcion("\n" + "No se encontró ningún libro con el título " + tituloLibro + "\n");
        }
        return libroEncontrado.get();
    }

    private void buscarLibrosPorTitulo() {
        try {
            DatosLibros datos = getDatosLibros();

            //Verificar si el libro ya existe en la base de datos
            Optional<Libro> libroExistente = libroRepositorio.findByTitulo(datos.titulo());
            if (libroExistente.isPresent()) {
                throw new LibroYaExisteException("\n" + "El libro " + datos.titulo() + " ya está en la base de datos." + "\n");
            }

            List<Autor> autores = datos.autor().stream()
                    .map(datosAutor -> {
                        Optional<Autor> autorExistente = autorRepositorio.findByNombre(datosAutor.nombre());
                        return autorExistente.orElseGet(() -> {
                            Autor nuevoAutor = new Autor();
                            nuevoAutor.setNombre(datosAutor.nombre());
                            nuevoAutor.setFechaDeNacimiento(datosAutor.fechaNacimiento());
                            nuevoAutor.setFechaDeFallecimiento(datosAutor.fechaFallecimiento());
                            autorRepositorio.save(nuevoAutor);
                            return nuevoAutor;
                        });
                    })
                    .collect(Collectors.toList());

            Libro libro = new Libro(datos);
            libro.setAutor(autores.get(0));
            libroRepositorio.save(libro);
            System.out.println("Libro Guardado exitosamente");
            System.out.println("----- Libro -----");
            System.out.println(libro);
        } catch (LibroNoEncontradoExcepcion e){
            System.out.println(e.getMessage());
        } catch (LibroYaExisteException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarLibrosRegistrados() {

        try{
            List<Libro> libros = libroRepositorio.findAll();
            if (libros.isEmpty()){
                System.out.println("No hay libros registrados por el momento");
            } else {
                System.out.println("\n" + "Libros Registrados: " + "\n");
                libros.forEach(l -> System.out.println(l.toString()));
            }

        }catch (Exception e) {
            System.out.println("Ocurrió un error al recuperar los libros: " + e.getMessage());
        }
    }

    private void listarAutoresRegistrados() {
        try{
            List<Autor> autoresRegistrados = autorRepositorio.findAllWithLibros();
            if (autoresRegistrados.isEmpty()){
                System.out.println("No hay autores registrados por el momento");
            }else {
                System.out.println("\n" + "Autores Registrados: " + "\n");
                autoresRegistrados.forEach(a -> System.out.println(a.toString()));
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al recuperar a los autores: " + e.getMessage());
        }
    }

    private void listarAutoresVivosEnFechaDeterminada() {
        System.out.println("\n" + "Ingrese el año en el que desea buscar, para ver que autores estuvieron vivos");

        try {
            int fechaConsulta = lectura.nextInt();

            //Busca los autores vivos en el año indicado por el usuario
            List<Autor> traerAutoresPorAño = autorRepositorio.findAutoresByFecha(fechaConsulta);

            if (!traerAutoresPorAño.isEmpty()) {
                System.out.println("\n" + "Autores vivos en " + fechaConsulta + "\n");
                traerAutoresPorAño.forEach(a -> System.out.println("\n" + a.toString()));
            } else {
                System.out.println("\n" + "No se encontraron autores vivos en el año " + fechaConsulta + "\n");
            }
        } catch (InputMismatchException e) {
            System.out.println("\n" + "La fecha ingresada no es válida. Intenta ingresar nuevamente el año de búsqueda" + "\n");
            lectura.next();
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("\n" + "Escriba el idioma del libro que desea buscar" + "\n");
        System.out.println("es- Español");
        System.out.println("en- Inglés");
        System.out.println("fr- Francés");
        System.out.println("pt- Portugués" + "\n");


        String consultaIdioma = lectura.nextLine();

        try {
            //convierte la entrada del usuario al enum Idiomas
            var nombreIdioma = Idiomas.fromUsuario(consultaIdioma);

            //Busca los libros por el idioma
            List<Libro> libroPorIdioma = libroRepositorio.findByIdioma(nombreIdioma);

            if (!libroPorIdioma.isEmpty()) {
                System.out.println("\n" + "Libros encontrados en " + nombreIdioma + ":");
                libroPorIdioma.forEach(l -> System.out.println("\n" + l.toString()));
            } else {
                System.out.println("\n" + "No se encontraron libros en el idioma buscado" + "\n");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + "El idioma ingresado no es válido. Intente nuevamente con una de las opciones disponibles" + "\n");
        }

    }
}