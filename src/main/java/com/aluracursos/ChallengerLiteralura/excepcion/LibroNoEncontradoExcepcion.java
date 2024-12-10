package com.aluracursos.ChallengerLiteralura.excepcion;

public class LibroNoEncontradoExcepcion extends RuntimeException {
    public LibroNoEncontradoExcepcion(String mensaje) {
        super(mensaje);
    }
}
