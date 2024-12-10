package com.aluracursos.ChallengerLiteralura.excepcion;

public class LibroYaExisteException extends RuntimeException {
    public LibroYaExisteException(String mensaje) {
        super(mensaje);
    }
}
